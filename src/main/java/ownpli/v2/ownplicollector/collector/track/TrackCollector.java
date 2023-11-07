package ownpli.v2.ownplicollector.collector.track;

import com.neovisionaries.i18n.CountryCode;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.core5.http.ParseException;
import org.springframework.stereotype.Service;
import ownpli.v2.ownplicollector.dto.SpotifyToken;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.Track;
import se.michaelthelin.spotify.requests.data.search.simplified.SearchTracksRequest;

import java.io.IOException;

@Service
@Slf4j
@RequiredArgsConstructor
public class TrackCollector {
    private final SpotifyToken token;

    @PostConstruct
    public void searchTracks() {
        String q = "year:2020-2023";
        int offset = 0;
        int limit = 50;

        SpotifyApi spotifyApi = new SpotifyApi.Builder()
                .setAccessToken(token.getToken())
                .build();
        try {
            Paging<Track> trackPaging;
            do {
                SearchTracksRequest request = spotifyApi.searchTracks(q)
                        .market(CountryCode.KR)
                        .limit(limit)
                        .offset(offset)
                        .build();

                trackPaging = request.execute();
                for (Track track : trackPaging.getItems()) {
                    log.info("Track: " + track.getName());
                    log.info("Artists: " + track.getArtists()[0].getName());
                    log.info("Release Date: " + track.getAlbum().getReleaseDate());
                    log.info("Track ID: " + track.getId());
                    log.info("-----------------------------------");
                }

                offset += limit; // 다음 페이지로 이동

            } while (trackPaging.getNext() != null);
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            log.info("Error: " + e.getMessage());
        }
    }
}
