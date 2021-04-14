package top.yangcc.config;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * 消息队列配置类
 * @author yangcc
 * 创建两个队列:
 *    1. 超时队列
 *    2. 消费队列
 *    3. 创建交换机
 */
@Configuration
public class RabbitMqConfig {

    /**交换机*/
    public static final String MEETING_EXCHANGE = "meeting_exchange";
    /**队列*/
    public static final String MEETING_QUEUE = "meeting_queue";
    /** routing key */
    public  static final  String ROUTING_KEY_MEETING = "routing_key_meeting";

    /** 死信交换机 */
    public static final  String DEAL_EXCHANGE_MEETING = "deal_exchange_meeting";
    /**死信消息队列名称*/
    public static  final  String DEAL_QUEUE_MEETING = "deal_queue_meeting";
    /**死信 routingKey */
    public  static final  String DEAD_ROUTING_KEY_MEETING = "dead_routing_key_meeting";

    /**死信队列 交换机标识符*/
    public static final String DEAD_LETTER_QUEUE_KEY = "x-dead-letter-exchange";

    /**死信队列交换机绑定键标识符*/
    public static final String DEAD_LETTER_ROUTING_KEY = "x-dead-letter-routing-key";




    /**声明一个direct类型的交换机*/
    @Bean
    DirectExchange meetingExchange() {
        return new DirectExchange(MEETING_EXCHANGE);
    }

    @Bean
    public Queue meetingQueue() {
        // 将普通队列绑定到死信队列交换机上
        Map<String, Object> args = new HashMap<>(2);
        args.put(DEAD_LETTER_QUEUE_KEY, DEAL_EXCHANGE_MEETING);
        args.put(DEAD_LETTER_ROUTING_KEY, DEAD_ROUTING_KEY_MEETING);
        return new Queue(MEETING_QUEUE, true, false, false, args);
    }


    /**绑定Queue队列到交换机,并且指定routingKey*/
    @Bean
    Binding bindingDirectExchange() {
        return BindingBuilder
                .bind(meetingQueue())
                .to(meetingExchange())
                .with(ROUTING_KEY_MEETING);
    }

    /**创建死信交换机*/
    @Bean
    public DirectExchange deadExchangeMeeting() {
        return new DirectExchange(DEAL_EXCHANGE_MEETING);
    }


    /**创建配置死信队列*/
    @Bean
    public Queue deadQueueMeeting() {
        return new Queue(DEAL_QUEUE_MEETING, true);
    }


    /**死信队列与死信交换机绑定*/
    @Bean
    public Binding bindingDeadExchange() {
        return BindingBuilder
                .bind(deadQueueMeeting())
                .to(deadExchangeMeeting())
                .with(DEAD_ROUTING_KEY_MEETING);
    }
}
