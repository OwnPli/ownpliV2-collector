package ownpli.v2.ownplicollector.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class Album {
    private String albumTitle;
    private List<Artist> artist;
    private String image;
    private LocalDate relaseDate;
    private String spotifyKey;

    @Builder
    public Album(String albumTitle, List<Artist> artist, String image, LocalDate relaseDate, String spotifyKey) {
        this.albumTitle = albumTitle;
        this.artist = artist;
        this.image = image;
        this.relaseDate = relaseDate;
        this.spotifyKey = spotifyKey;
    }
}
