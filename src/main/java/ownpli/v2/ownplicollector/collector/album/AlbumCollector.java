package ownpli.v2.ownplicollector.collector.album;


import lombok.AllArgsConstructor;
import lombok.Builder;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.neovisionaries.i18n.CountryCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.core5.http.ParseException;
import ownpli.v2.ownplicollector.dto.QueryPrameter;
import ownpli.v2.ownplicollector.dto.SpotifyToken;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.AlbumSimplified;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.requests.data.search.simplified.SearchAlbumsRequest;

@Slf4j
@Builder
@AllArgsConstructor
public class AlbumCollector {

    private String year;
    private String artist;
    private String track;
    private String genre;
    private String album;
    private SpotifyToken spotifyToken;


    public void execute() {
        int offset = 0;
        int limit = 50; // 한 페이지에 표시할 앨범 수

        SpotifyApi spotifyApi = new SpotifyApi.Builder()
                .setAccessToken(spotifyToken.getToken()) // 여기에 액세스 토큰을 설정
                .build();

        try {
            Paging<AlbumSimplified> albumSimplifiedPaging;

            do {
                // 새로운 검색 요청을 생성하고 offset을 설정
                SearchAlbumsRequest request = spotifyApi
                        .searchAlbums(buildQuery())
                        .market(CountryCode.KR)
                        .limit(limit)
                        .offset(offset)
                        .build();

                albumSimplifiedPaging = request.execute();
                for (AlbumSimplified album : albumSimplifiedPaging.getItems()) {
                    log.info("Album: " + album.getName());
                    log.info("Artists: " + album.getArtists()[0].getName()); // 여기서는 첫 번째 아티스트만 출력
                    log.info("Release Date: " + album.getReleaseDate());
                    log.info("Album ID: " + album.getId());
                    log.info("-----------------------------------");
                }

                offset += limit; // 다음 페이지로 이동

            } while (albumSimplifiedPaging.getNext() != null); // 다음 페이지가 없을 때까지 반복

        } catch (IOException | SpotifyWebApiException | ParseException e) {
            log.info("Error: " + e.getMessage());
        }
    }

    private String buildQuery() throws UnsupportedEncodingException {
        return Stream.of(
                        QueryPrameter.of("year", year),
                        QueryPrameter.of("artist", artist),
                        QueryPrameter.of("track", track),
                        QueryPrameter.of("genre" ,genre),
                        QueryPrameter.of("album", album))
                .filter(QueryPrameter::isNotNull)
                .map(QueryPrameter::getQuery)
                .collect(Collectors.joining(" "));
    }
}
