package ownpli.v2.ownplicollector.postconstructor.album;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ownpli.v2.ownplicollector.collector.album.AlbumCollector;
import ownpli.v2.ownplicollector.dto.CollectorDto;
import ownpli.v2.ownplicollector.dto.SpotifyToken;
import ownpli.v2.ownplicollector.rabbitmq.AlbumProducer;

@Service
@RequiredArgsConstructor
public class AlbumInitializer {

    private final AlbumProducer albumProducer;
    private final SpotifyToken spotifyToken;

    @PostConstruct
    public void init(){
        AlbumCollector albumCollector = new AlbumCollector(spotifyToken);


       albumProducer.produce(
               albumCollector.execute(
                       CollectorDto.builder()
                               .year("2020-2023")
                               .build()
               )
       );


    }
}
