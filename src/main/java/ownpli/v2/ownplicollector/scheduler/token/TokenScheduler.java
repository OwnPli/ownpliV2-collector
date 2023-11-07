package ownpli.v2.ownplicollector.scheduler.token;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ownpli.v2.ownplicollector.dto.SpotifyToken;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenScheduler {

    private final SpotifyToken spotifyToken;
    private final TokenGenerator tokenGenerator;

    /**
     * 1시간에 1번씩 토큰 생성
     */
    @Scheduled(cron = "0 0 0/1 * * *")
    public void scheduler() {
        spotifyToken.setToken(tokenGenerator.accessToken());
    }
}

