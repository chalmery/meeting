package top.yangcc.mq;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Component;
import top.yangcc.config.RabbitMqConfig;
import top.yangcc.entity.Meeting;
import top.yangcc.mapper.MeetingMapper;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

/**
 * 消息队列的消费者,处理会议的状态
 * @author yangcc
 */
@Component
public class MeetingFailureListener {
    private static final String NO_STARTED = "未进行";
    private static final String ONGOING = "正在进行";
    private static final String OVER = "已经结束";

    private MeetingMapper meetingMapper;

    @Autowired
    public MeetingFailureListener(MeetingMapper meetingMapper) {
        this.meetingMapper = meetingMapper;
    }

    //设置会议失效的队列
    @RabbitListener(queues = RabbitMqConfig.DEAL_QUEUE_MEETING)
    public void process(Meeting meeting, Message message,
                        @Headers Map<String, Object> headers,
                        Channel channel) throws IOException {
        //查询当前会议状态
        String status = meetingMapper.findStatusById(meeting.getId());
        //查询会议状态
        System.out.println("======会议的状态======当前状态为:" +status);
        //如果是会议没有开始,修改为正在进行
        if (Objects.equals(status,NO_STARTED)){
            meeting.setStatus(ONGOING);
        }
        //如果是正在进行,修改为已经结束
        else if(Objects.equals(status,ONGOING)){
            meeting.setStatus(OVER);
        }
        System.out.println("======会议的状态======修改后的状态为:" +meeting.getStatus());
        //修改状态
        meetingMapper.editStatus(meeting);
        // 手动ack
        Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
        // 手动签收 参数：1：拿到消息；2：是否批量操作
        channel.basicAck(deliveryTag, false);
    }
}
