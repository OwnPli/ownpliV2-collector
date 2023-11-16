package ownpli.v2.ownplicollector.collector.playlist;

import com.neovisionaries.i18n.CountryCode;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.core5.http.ParseException;
import ownpli.v2.ownplicollector.dto.SpotifyToken;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.PlaylistTrack;
import se.michaelthelin.spotify.requests.data.playlists.GetPlaylistsItemsRequest;
import java.io.IOException;


@Slf4j
@RequiredArgsConstructor
public class PlaylistTrackCollector {

    private final SpotifyToken spotifyToken;

    public void execute(String playlistId) {
        int offset = 0;
        int limit = 50; // 한 페이지에 표시할 앨범 수

        SpotifyApi spotifyApi = new SpotifyApi.Builder()
                .setAccessToken(spotifyToken.getToken()) // 여기에 액세스 토큰을 설정
                .build();

        Paging<PlaylistTrack> playlistTrackPaging;

        try {
            do{
                GetPlaylistsItemsRequest request = spotifyApi
                    .getPlaylistsItems(playlistId)
                    .market(CountryCode.KR)
                    .limit(limit)
                    .offset(offset)
                    .build();

                playlistTrackPaging = request.execute();

                PlaylistTrack[] playlistTrack = playlistTrackPaging.getItems();

                for (PlaylistTrack track : playlistTrack){
                    log.info("Track: "+track.getTrack().getName());
                }
                offset += limit;
            } while (playlistTrackPaging.getNext() != null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SpotifyWebApiException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
