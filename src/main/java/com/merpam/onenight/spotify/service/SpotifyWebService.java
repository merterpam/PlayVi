package com.merpam.onenight.spotify.service;

import com.merpam.onenight.spotify.service.model.CreatePlaylistResponse;
import com.merpam.onenight.spotify.service.model.SongResponse;
import com.merpam.onenight.spotify.service.model.SearchTracksResponse;

public interface SpotifyWebService {

    SearchTracksResponse getSongs(String query, String limit, String offset);

    SongResponse getSong(String id);

    void removeSongFromPlaylist(String spotifyId, String songId);

    void addSongToPlaylist(String spotifyId, String songId);

    CreatePlaylistResponse createPlayList(String name);

    void deletePlaylist(String id);
}
