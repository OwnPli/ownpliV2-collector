package ownpli.v2.ownplicollector.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;
@Getter
public class TrackParameter {
    private String trackTitle;
    private String lyricist;
    private String songWriter;
    private String spotifyKey;
    private List<Artist> artist;

    @Builder
    public TrackParameter(String trackTitle, String lyricist, String songWriter, String spotifyKey, List<Artist> artist) {
        this.trackTitle = trackTitle;
        this.lyricist = lyricist;
        this.songWriter = songWriter;
        this.spotifyKey = spotifyKey;
        this.artist = artist;
    }
}
