package ownpli.v2.ownplicollector.postconstructor.album;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ownpli.v2.ownplicollector.collector.album.AlbumCollector;
import ownpli.v2.ownplicollector.dto.SpotifyToken;
@Service
@RequiredArgsConstructor
public class AlbumInitializer {
    private final SpotifyToken token;
    @PostConstruct
    public void init(){
        AlbumCollector albumCollector = AlbumCollector.builder()
                .year("2020-2023")
                .spotifyToken(token)
                .build();
        albumCollector.execute();
    }
}
