package ownpli.v2.ownplicollector.postconstructor.track;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ownpli.v2.ownplicollector.collector.track.TrackCollector;
import ownpli.v2.ownplicollector.dto.CollectorDto;
import ownpli.v2.ownplicollector.dto.SpotifyToken;
import ownpli.v2.ownplicollector.rabbitmq.TrackProducer;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class TrackInitializer {

    private final TrackProducer trackProducer;
    private final SpotifyToken spotifyToken;
    @PostConstruct
    public void init(){
        TrackCollector trackCollector = new TrackCollector(spotifyToken);

        trackProducer.produce(
                trackCollector.execute(
                        CollectorDto.builder()
                                .year("2020-2023")
                                .build()
                )
        );
    }
}
