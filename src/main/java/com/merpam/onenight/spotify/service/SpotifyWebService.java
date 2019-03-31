package com.merpam.onenight.spotify.service;

import com.merpam.onenight.spotify.service.model.CreatePlaylistResponse;
import com.merpam.onenight.spotify.service.model.SongResponse;
import com.merpam.onenight.spotify.service.model.SearchTracksResponse;

public interface SpotifyWebService {

    SearchTracksResponse getSongs(String query, String limit, String offset);

    SongResponse getSong(String id);

    void removeSongFromPlaylist(String playlistId, String songId, int position);

    void addSongToPlaylist(String playlistId, String songUri);

    CreatePlaylistResponse createPlayList(String name);

    void deletePlaylist(String id);
}
