package ownpli.v2.ownplicollector.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CollectorDto {

    private String year;
    private String artist;
    private String track;
    private String genre;
    private String album;

    @Builder
    public CollectorDto(String year, String artist, String track, String genre, String album) {
        this.year = year;
        this.artist = artist;
        this.track = track;
        this.genre = genre;
        this.album = album;
    }
}
