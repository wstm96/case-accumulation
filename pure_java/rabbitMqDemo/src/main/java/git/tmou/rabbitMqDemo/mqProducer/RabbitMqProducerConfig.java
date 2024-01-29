package git.tmou.rabbitMqDemo.mqProducer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
//@Slf4j
public class RabbitMqProducerConfig {

    public static Logger log = LoggerFactory.getLogger(RabbitMqProducerConfig.class);

    @Autowired
    RabbitTemplate rabbitTemplate;

    @PostConstruct
    public void init(){
        // 确认消息是否到达交换机
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
//            assert correlationData != null;
            if (ack) {
                //消息成功到达交换机
                log.info("exchange receive message success: "+ correlationData.getId());
            } else {
                log.error("exchange receive message failed: " + correlationData.getId());
            }
        });
        // 确认消息是否到达队列，到达队列该方法不执行
        this.rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
            log.warn("消息没有到达队列，来自于交换机：{}，路由键：{}，消息内容：{}", exchange, routingKey, new String(message.getBody()));
        });
    }

    @Bean
    public TopicExchange myFirstExchange(){
        return ExchangeBuilder.topicExchange("My_topic_exchange").build();
    }

    @Bean
    public Queue myFirstQueue(){
        return QueueBuilder.durable("My_first_queue")
                .build();
    }

    @Bean
    public Binding myFirstBinding(){
        return BindingBuilder.bind(myFirstQueue()).to(myFirstExchange()).with("first.*.*");
    }
}
