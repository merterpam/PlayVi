package com.merpam.onenight.spotify.service.model;

public class DeleteSongRequest {

    private DeleteTrackRequest[] tracks;

    public DeleteTrackRequest[] getTracks() {
        return tracks;
    }

    public void setTracks(DeleteTrackRequest[] tracks) {
        this.tracks = tracks;
    }
}
