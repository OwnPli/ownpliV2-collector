package ownpli.v2.ownplicollector.postconstructor.track;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ownpli.v2.ownplicollector.collector.track.TrackCollector;
import ownpli.v2.ownplicollector.dto.SpotifyToken;

import javax.annotation.PostConstruct;
@Slf4j
@Service
@RequiredArgsConstructor
public class TrackInitializer {
    private final SpotifyToken token;
    @PostConstruct
    public void init(){
        TrackCollector trackCollector = TrackCollector.builder()
                .year("2020-2023")
                .spotifyToken(token)
                .build();
        trackCollector.execute();
    }
}
