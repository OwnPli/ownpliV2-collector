package ownpli.v2.ownplicollector.postconstructor.album;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ownpli.v2.ownplicollector.collector.album.AlbumCollector;
import ownpli.v2.ownplicollector.dto.SpotifyToken;
import ownpli.v2.ownplicollector.rabbitmq.RabbitProducer;
import ownpli.v2.ownplicollector.rabbitmq.RabbitProperties;

@Service
@RequiredArgsConstructor
public class AlbumInitializer {
    private final RabbitProducer rabbitProducer;
    private final RabbitProperties rabbitProperties;

    @PostConstruct
    public void init(){
        AlbumCollector albumCollector = AlbumCollector.builder()
                .year("2020-2023")
                .build();
        albumCollector.execute();


        // RabbitMQ 테스트용
        String rabbitExchange = rabbitProperties.getMusicExchange();
        String rabbitRoutingKey = rabbitProperties.getAlbumRoutingKey();
        rabbitProducer.produce(rabbitExchange, rabbitRoutingKey, "test");
    }
}
