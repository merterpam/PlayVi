package com.merpam.onenight.spotify.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Tracks {
    private Song[] items;

    public Song[] getItems() {
        return items;
    }

    public void setItems(Song[] items) {
        this.items = items;
    }
}
