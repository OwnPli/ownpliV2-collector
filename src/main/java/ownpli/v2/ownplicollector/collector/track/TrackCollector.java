package ownpli.v2.ownplicollector.collector.track;

import com.neovisionaries.i18n.CountryCode;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.core5.http.ParseException;
import ownpli.v2.ownplicollector.dto.Artist;
import ownpli.v2.ownplicollector.dto.QueryPrameter;
import ownpli.v2.ownplicollector.dto.SpotifyToken;

import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.Track;
import se.michaelthelin.spotify.requests.data.search.simplified.SearchTracksRequest;


import java.io.IOException;
import java.io.UnsupportedEncodingException;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Slf4j
@AllArgsConstructor
@Builder
public class TrackCollector {

    private String year;
    private String artist;
    private String track;
    private String genre;
    private SpotifyToken spotifyToken;

    @Builder
    public TrackCollector(String year, String artist, String track, String genre) {
        this.year = year;
        this.artist = artist;
        this.track = track;
        this.genre = genre;
    }

    public void execute() {
        int offset = 0;
        int limit = 50;

        SpotifyApi spotifyApi = new SpotifyApi.Builder()
                .setAccessToken(spotifyToken.getToken())
                .build();
        log.info("trackCollector token:" + spotifyToken.getToken());
        try {
            Paging<Track> trackPaging;
            do {
                SearchTracksRequest request = spotifyApi
                        .searchTracks(buildQuery())
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

    private String buildQuery() throws UnsupportedEncodingException {
        return Stream.of(
                        QueryPrameter.of("year", year),
                        QueryPrameter.of("artist", artist),
                        QueryPrameter.of("track", track),
                        QueryPrameter.of("genre" ,genre))
                .filter(QueryPrameter::isNotNull)
                .map(QueryPrameter::getQuery)
                .collect(Collectors.joining(" "));
    }

}
