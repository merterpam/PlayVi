package com.merpam.onenight.spotify.service.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TracksResponse {
    private SongResponse[] items;

    public SongResponse[] getItems() {
        return items;
    }

    public void setItems(SongResponse[] items) {
        this.items = items;
    }
}
