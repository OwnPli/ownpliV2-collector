package ownpli.v2.ownplicollector.scheduler.token;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.core5.http.ParseException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.ClientCredentials;
import se.michaelthelin.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;

import java.io.IOException;
import ownpli.v2.ownplicollector.dto.ClientInformation;

@Slf4j
@Service
@Component
@RequiredArgsConstructor
public class TokenGenerator {

    private final ClientInformation client;

    private SpotifyApi spotifyApi;
    public String accessToken() {
        initializeSpotifyApi();
        ClientCredentialsRequest clientCredentialsRequest = spotifyApi.clientCredentials().build();

        try {
            final ClientCredentials clientCredentials = clientCredentialsRequest.execute();
            // Set access token for further "spotifyApi" object usage
            spotifyApi.setAccessToken(clientCredentials.getAccessToken());
            return spotifyApi.getAccessToken();

        } catch (IOException | SpotifyWebApiException | ParseException e) {
            log.info("Error: " + e.getMessage());
            return "error";
        }
    }

    private void initializeSpotifyApi() {
        spotifyApi = new SpotifyApi.Builder()
                .setClientId(client.getClientId())
                .setClientSecret(client.getClientSecret())
                .build();
    }

}
