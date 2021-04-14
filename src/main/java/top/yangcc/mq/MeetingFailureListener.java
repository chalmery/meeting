package top.yangcc.mq;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Component;
import top.yangcc.config.RabbitMqConfig;

import java.io.IOException;
import java.util.Map;

/**
 * @author yangcc
 */
@Component
public class MeetingFailureListener {
    //设置会议失效的队列
    @RabbitListener(queues = RabbitMqConfig.DEAL_QUEUE_MEETING)
    public void process(Integer id, Message message,
                        @Headers Map<String, Object> headers,
                        Channel channel) throws IOException {
        System.out.println("======修改会议的状态为正在进行======");
        System.out.println(id);
        // 手动ack
        Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
        // 手动签收 参数：1：拿到消息；2：是否批量操作
        channel.basicAck(deliveryTag, false);
    }
}
