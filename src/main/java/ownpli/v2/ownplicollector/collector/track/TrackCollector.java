package ownpli.v2.ownplicollector.collector.track;

import com.neovisionaries.i18n.CountryCode;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.core5.http.ParseException;
import ownpli.v2.ownplicollector.dto.*;
import ownpli.v2.ownplicollector.dto.QueryPrameter;
import ownpli.v2.ownplicollector.dto.SpotifyToken;

import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.ArtistSimplified;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.Track;
import se.michaelthelin.spotify.requests.data.search.simplified.SearchTracksRequest;


import java.io.IOException;
import java.io.UnsupportedEncodingException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Slf4j
@RequiredArgsConstructor
public class TrackCollector {

    private final SpotifyToken spotifyToken;


    public List<TrackParameter> execute(CollectorDto trackCollectorDto) {
        int offset = 0;
        int limit = 50;

        List<TrackParameter> trackParameterList= new ArrayList<>();
        SpotifyApi spotifyApi = new SpotifyApi.Builder()
                .setAccessToken(spotifyToken.getToken())
                .build();

        try {
            Paging<Track> trackPaging;
            do {
                SearchTracksRequest request = spotifyApi
                        .searchTracks(buildQuery(trackCollectorDto))
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

                    List<Artist> artistList = new ArrayList<>();
                    for (ArtistSimplified artist : track.getArtists()) {
                        Artist artistDto = Artist.builder()
                                .artistKey(artist.getId())
                                .artistName(artist.getName())
                                .build();
                        artistList.add(artistDto);
                    }
                    TrackParameter trackParameter = TrackParameter.builder()
                            .trackTitle(track.getName())
                            .spotifyKey(track.getId())
                            .artist(artistList)
                            .build();
                    trackParameterList.add(trackParameter);
                }

                offset += limit; // 다음 페이지로 이동

            } while (trackPaging.getNext() != null);
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            log.info("Error: " + e.getMessage());
        }
        return trackParameterList;
    }

    private String buildQuery(CollectorDto trackCollectorDto) throws UnsupportedEncodingException {
        return Stream.of(
                        QueryPrameter.of("year",trackCollectorDto.getYear() ),
                        QueryPrameter.of("artist", trackCollectorDto.getArtist()),
                        QueryPrameter.of("track", trackCollectorDto.getTrack()),
                        QueryPrameter.of("genre" ,trackCollectorDto.getGenre()))
                .filter(QueryPrameter::isNotNull)
                .map(QueryPrameter::getQuery)
                .collect(Collectors.joining(" "));
    }

}
