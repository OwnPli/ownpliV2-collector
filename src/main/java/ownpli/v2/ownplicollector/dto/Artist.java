package ownpli.v2.ownplicollector.dto;

import lombok.Builder;

public class Artist {
    private String artistKey;
    private String artistName;

    @Builder
    public Artist(String artistKey, String artistName) {
        this.artistKey = artistKey;
        this.artistName = artistName;
    }


}
