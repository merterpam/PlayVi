package com.merpam.onenight.spotify;

import com.merpam.onenight.spotify.pojo.TracksResponse;

public interface SpotifyWebService {

    TracksResponse getSongs(String query, String limit, String offset);
}
