package ownpli.v2.ownplicollector.rabbitmq;

import org.springframework.stereotype.Component;
import ownpli.v2.ownplicollector.dto.TrackParameter;

import java.util.List;
@Component
public class TrackProducer {
    private final Producer<TrackParameter> producer;
    private final RabbitProperties rabbitProperties;

    public TrackProducer(Producer<TrackParameter> producer, RabbitProperties rabbitProperties) {
        this.producer = producer;
        this.rabbitProperties = rabbitProperties;
    }

    public void produce(List<TrackParameter> trackParameterList){
        producer.produce(rabbitProperties.getMusicExchange(),
                rabbitProperties.getTrackRoutingKey(), trackParameterList);
    }
}
