package ownpli.v2.ownplicollector.initializer.album;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;


import java.io.IOException;

import com.neovisionaries.i18n.CountryCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.core5.http.ParseException;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.AlbumSimplified;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.requests.data.search.simplified.SearchAlbumsRequest;
@Service
@Slf4j
@RequiredArgsConstructor
public class AlbumInfoInitializer {

    @PostConstruct
    public void searchAllAlbums() {
        String q = "year:2020-2023";
        int offset = 0;
        int limit = 50; // 한 페이지에 표시할 앨범 수

        SpotifyApi spotifyApi = new SpotifyApi.Builder()
                .setAccessToken("YOUR_ACCESS_TOKEN_HERE") // 여기에 액세스 토큰을 설정
                .build();

        try {
            Paging<AlbumSimplified> albumSimplifiedPaging;

            do {
                // 새로운 검색 요청을 생성하고 offset을 설정
                SearchAlbumsRequest request = spotifyApi.searchAlbums(q)
                        .market(CountryCode.KR)
                        .limit(limit)
                        .offset(offset)
                        .build();

                albumSimplifiedPaging = request.execute();
                for (AlbumSimplified album : albumSimplifiedPaging.getItems()) {
                    log.info("Album: " + album.getName());
                    log.info("Artists: " + album.getArtists()[0].getName()); // 여기서는 첫 번째 아티스트만 출력
                    log.info("Release Date: " + album.getReleaseDate());
                }

                offset += limit; // 다음 페이지로 이동

            } while (albumSimplifiedPaging.getNext() != null); // 다음 페이지가 없을 때까지 반복

        } catch (IOException | SpotifyWebApiException | ParseException e) {
            log.info("Error: " + e.getMessage());
        }
    }
}
