package com.merpam.onenight.spotify.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Album {

    private String id;
    
    private Image[] images;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Image[] getImages() {
        return images;
    }

    public void setImages(Image[] images) {
        this.images = images;
    }
}
