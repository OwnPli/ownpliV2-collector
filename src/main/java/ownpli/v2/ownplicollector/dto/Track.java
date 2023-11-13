package ownpli.v2.ownplicollector.dto;

import lombok.Builder;

import java.util.List;

public class Track {
    private String trackTitle;
    private String lyricist;
    private String songWriter;
    private String spotifyKey;
    private List<Artist> artist;
    @Builder
    public Track(String trackTitle, String lyricist, String songWriter, String spotifyKey, List<Artist> artist) {
        this.trackTitle = trackTitle;
        this.lyricist = lyricist;
        this.songWriter = songWriter;
        this.spotifyKey = spotifyKey;
        this.artist = artist;
    }
}
