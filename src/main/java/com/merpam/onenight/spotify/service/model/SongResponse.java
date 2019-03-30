package com.merpam.onenight.spotify.service.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SongResponse {

    private String id;

    private String name;

    private int duration_ms;

    private ArtistResponse[] artists;

    private AlbumResponse album;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArtistResponse[] getArtists() {
        return artists;
    }

    public void setArtists(ArtistResponse[] artists) {
        this.artists = artists;
    }

    public int getDuration_ms() {
        return duration_ms;
    }

    public void setDuration_ms(int duration_ms) {
        this.duration_ms = duration_ms;
    }

    public AlbumResponse getAlbum() {
        return album;
    }

    public void setAlbum(AlbumResponse album) {
        this.album = album;
    }
}
