package com.merpam.onenight.utils;

import com.merpam.onenight.persistence.model.Party;
import com.merpam.onenight.persistence.model.Song;
import com.merpam.onenight.persistence.model.User;
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

    public static boolean isSongRemovable(Party party, String songUri, int position, User user) {
        Song song = party.getSongList().get(position);

        return song.getUri().equals(songUri) && (song.getCreator() == user || party.getCreator() == user);

    }
}
