package ownpli.v2.ownplicollector.rabbitmq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class RabbitProducer<T> implements Producer<T>{
    private final RabbitTemplate rabbitTemplate;

    public RabbitProducer(RabbitTemplate rabbitTemplate){
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void produce(String exchange, String routingKey, T message){
        rabbitTemplate.convertAndSend(exchange, routingKey, message);
    }

}
