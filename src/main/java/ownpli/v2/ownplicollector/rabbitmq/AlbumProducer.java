package ownpli.v2.ownplicollector.rabbitmq;

import org.springframework.stereotype.Component;
import ownpli.v2.ownplicollector.dto.AlbumParameter;

import java.util.List;

@Component
public class AlbumProducer {
    private final Producer<AlbumParameter> producer;
    private final RabbitProperties rabbitProperties;

    public AlbumProducer(Producer<AlbumParameter> producer, RabbitProperties rabbitProperties) {
        this.producer = producer;
        this.rabbitProperties = rabbitProperties;
    }

    public void produce(List<AlbumParameter> albumList){
        producer.produce(rabbitProperties.getMusicExchange(),
                rabbitProperties.getAlbumRoutingKey(), albumList);
    }
}
