package ownpli.v2.ownplicollector.dto;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Configuration;
import ownpli.v2.ownplicollector.scheduler.token.TokenGenerator;

@Setter
@Configuration
@RequiredArgsConstructor
public class SpotifyToken {

    private String token;
    private final TokenGenerator tokenGenerator;

    public String getToken() {
        if(isTokenEmpty()) {
            token = tokenGenerator.accessToken();
        }

        return token;
    }

    private boolean isTokenEmpty() {
        return token == null || token.isEmpty();
    }
}
