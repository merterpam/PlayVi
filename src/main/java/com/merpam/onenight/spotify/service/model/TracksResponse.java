package com.merpam.onenight.spotify.service.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TracksResponse {
    private String href;
    private List<SongResponse> items;

    public List<SongResponse> getItems() {
        return items;
    }

    public void setItems(List<SongResponse> items) {
        this.items = items;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }
}
