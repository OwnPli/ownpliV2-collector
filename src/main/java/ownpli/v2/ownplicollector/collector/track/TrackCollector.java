package ownpli.v2.ownplicollector.collector.track;

import com.neovisionaries.i18n.CountryCode;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.core5.http.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ownpli.v2.ownplicollector.dto.SpotifyToken;
import ownpli.v2.ownplicollector.scheduler.token.TokenGenerator;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.Track;
import se.michaelthelin.spotify.requests.data.search.simplified.SearchTracksRequest;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


@Slf4j
@AllArgsConstructor
@Builder
public class TrackCollector {

    private String year;
    private String artist;
    private String track;
    private String genre;
    SpotifyToken spotifyToken;

    public void execute(){
        int offset = 0;
        int limit = 50;

        SpotifyApi spotifyApi = new SpotifyApi.Builder()
                .setAccessToken(spotifyToken.getToken())
                .build();
        log.info("trackCollector token:"+spotifyToken.getToken());
        try {
            Paging<Track> trackPaging;
            do {
                SearchTracksRequest request = spotifyApi.searchTracks(buildQuery())
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


    private String buildQuery() {
        try {
            StringBuilder queryBuilder = new StringBuilder();

            if (year != null && !year.isEmpty()) {
                String encodedYear = URLEncoder.encode(year, "UTF-8");
                queryBuilder.append("year:").append(encodedYear).append(" ");
            }
            if (artist != null && !artist.isEmpty()) {
                String encodedArtist = URLEncoder.encode(artist, "UTF-8");
                queryBuilder.append("artist:").append(encodedArtist).append(" ");
            }
            if (track != null && !track.isEmpty()) {
                String encodedTrack = URLEncoder.encode(track, "UTF-8");
                queryBuilder.append("track:").append(encodedTrack).append(" ");
            }
            if (genre != null && !genre.isEmpty()) {
                String encodedGenre = URLEncoder.encode(genre, "UTF-8");
                queryBuilder.append("genre:").append(encodedGenre).append(" ");
            }

            return queryBuilder.toString().trim();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }
    }
}
