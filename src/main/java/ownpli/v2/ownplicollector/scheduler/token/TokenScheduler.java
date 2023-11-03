package ownpli.v2.ownplicollector.scheduler.token;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.antlr.v4.runtime.Token;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ownpli.v2.ownplicollector.dto.SpotifyToken;

import jakarta.transaction.Transactional;
import org.apache.hc.core5.http.ParseException;
import ownpli.v2.ownplicollector.dto.ClientInformation;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.ClientCredentials;
import se.michaelthelin.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;

import java.io.IOException;

@Slf4j
@Service
@Transactional
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

