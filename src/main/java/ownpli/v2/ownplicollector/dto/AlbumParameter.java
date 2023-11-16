package ownpli.v2.ownplicollector.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class AlbumParameter {
    private String albumTitle;
    private List<Artist> artist;
    private String image;

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate releaseDate;
    private String spotifyKey;

    @Builder
    public AlbumParameter(String albumTitle, List<Artist> artist, String image, LocalDate releaseDate, String spotifyKey) {
        this.albumTitle = albumTitle;
        this.artist = artist;
        this.image = image;
        this.releaseDate = releaseDate;
        this.spotifyKey = spotifyKey;
    }
}
