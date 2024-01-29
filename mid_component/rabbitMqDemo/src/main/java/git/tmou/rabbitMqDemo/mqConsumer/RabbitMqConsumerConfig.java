package git.tmou.rabbitMqDemo.mqConsumer;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
public class RabbitMqConsumerConfig {
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "My_first_queue", durable = "true"),
            exchange = @Exchange(value = "My_topic_exchange", ignoreDeclarationExceptions = "true", type = ExchangeTypes.TOPIC),
            key = {"a.b"}
    ))
    public void listener(String msg, Channel channel, Message message) throws IOException {
        try {
            System.out.println(msg);
            // 手动确认消息
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (IOException e) {
            e.printStackTrace();
            // 是否已经重试过
            if (message.getMessageProperties().getRedelivered()) {
                // 已重试过直接拒绝
                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
            } else {
                // 未重试过，重新入队
                channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
            }
        }
    }

    // 测试死信队列
//    @RabbitListener(queues = "SPRING_DEAD_QUEUE")
//    public void listener2(String msg, Channel channel, Message message) throws IOException {
//        try {
//            System.out.println(msg);
//            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
//        } catch (IOException e) {
//            e.printStackTrace();
//            if (message.getMessageProperties().getRedelivered()) {
//                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
//            } else {
//                channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
//            }
//        }
}
