package com.merpam.onenight.spotify.service.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AlbumResponse {

    private String id;
    
    private ImageResponse[] images;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ImageResponse[] getImages() {
        return images;
    }

    public void setImages(ImageResponse[] images) {
        this.images = images;
    }
}
