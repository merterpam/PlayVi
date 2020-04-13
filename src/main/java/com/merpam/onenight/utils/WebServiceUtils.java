package com.merpam.onenight.utils;

import com.merpam.onenight.persistence.model.PartyModel;
import com.merpam.onenight.persistence.model.SongModel;
import com.merpam.onenight.spotify.service.model.DeleteSongRequest;
import com.merpam.onenight.spotify.service.model.DeleteTrackRequest;

public class WebServiceUtils {

    private WebServiceUtils() {
        //empty constructor, not meant to be initialized
    }

    public static DeleteSongRequest generateDeleteRequest(String songId, int position) {
        DeleteSongRequest deleteSongRequest = new DeleteSongRequest();

        DeleteTrackRequest deleteTrackRequest = new DeleteTrackRequest();
        deleteTrackRequest.setUri("spotify:track:" + songId);

        int[] positions = {position};
        deleteTrackRequest.setPositions(positions);

        DeleteTrackRequest[] tracks = {deleteTrackRequest};
        deleteSongRequest.setTracks(tracks);

        return deleteSongRequest;
    }

    public static boolean isSongRemovable(PartyModel party, String songId, int position, String userId) {
        SongModel song = party.getSongList().get(position);

        return isCorrectSong(song, songId) && isUserAuthorizedForSongRemoval(party, song, userId);
    }

    private static boolean isCorrectSong(SongModel song, String songId) {
        return song.getId().equals(songId);
    }

    private static boolean isUserAuthorizedForSongRemoval(PartyModel party, SongModel song, String userId) {
        return song.getCreatorId().equals(userId) || party.getCreatorId().equals(userId);
    }
}
