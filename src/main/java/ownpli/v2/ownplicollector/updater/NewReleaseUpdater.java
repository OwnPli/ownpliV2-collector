package ownpli.v2.ownplicollector.updater;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ownpli.v2.ownplicollector.collector.playlist.PlaylistTrackCollector;
import ownpli.v2.ownplicollector.dto.SpotifyToken;


@Service
@RequiredArgsConstructor
public class NewReleaseUpdater {
    private final SpotifyToken spotifyToken;
    @Value("${new-release.id}")
    private String playlistId;

    // 일단 평일 오후 6시로 해놓음. 변경 가능
    @Scheduled(cron = "0 0 18 * * MON-FRI")
    public void update(){
        PlaylistTrackCollector playlistTrackCollector = new PlaylistTrackCollector(spotifyToken);
        playlistTrackCollector.execute(playlistId);
    }
}
