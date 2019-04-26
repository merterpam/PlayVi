package com.merpam.onenight.spotify.service.impl;

import com.merpam.onenight.constants.Constants;
import com.merpam.onenight.spotify.service.AbstractSpotifyWebService;
import com.merpam.onenight.spotify.service.SpotifyWebService;
import com.merpam.onenight.spotify.service.model.CreatePlaylistResponse;
import com.merpam.onenight.spotify.service.model.SearchTracksResponse;
import com.merpam.onenight.spotify.service.model.SongResponse;
import com.merpam.onenight.utils.WebServiceUtils;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SpotifyWebServiceImpl extends AbstractSpotifyWebService implements SpotifyWebService {

    private Logger LOGGER = LoggerFactory.getLogger(SpotifyWebServiceImpl.class);

    @Override
    @HystrixCommand(groupKey = Constants.HystrixGroups.SPOTIFY)
    public SearchTracksResponse getSongs(String query, String limit, String offset) {
        Map<String, Object> queryParam = new HashMap<>();
        queryParam.put("q", query + "*");
        queryParam.put("type", "track");
        queryParam.put("limit", limit);
        queryParam.put("offset", offset);
        //TODO cleanup

        SearchTracksResponse searchTracksResponse = doHttpGetRequest("/search", queryParam, SearchTracksResponse.class);
        List<SongResponse> songResponses = searchTracksResponse.getTracks().getItems();
        searchTracksResponse.getTracks().setItems(songResponses
                .stream()
                .sorted(Comparator.comparingInt(SongResponse::getPopularity).reversed())
                .collect(Collectors.toList()));

        return searchTracksResponse;
    }

    @Override
    @HystrixCommand(groupKey = Constants.HystrixGroups.SPOTIFY)
    public SongResponse getSong(String id) {
        return doHttpGetRequest("/tracks/" + id, Collections.emptyMap(), SongResponse.class);
    }

    @Override
    @HystrixCommand(groupKey = Constants.HystrixGroups.SPOTIFY)
    public void removeSongFromPlaylist(String playlistId, String songId, int position) {
        String path = "/playlists/" + playlistId + "/tracks";

        String response = doHttpDeleteRequest(path, WebServiceUtils.generateDeleteRequest(songId, position), String.class);
        LOGGER.info(response);
    }

    @Override
    @HystrixCommand(groupKey = Constants.HystrixGroups.SPOTIFY)
    public void addSongToPlaylist(String playlistId, String songUri) {
        String path = "/playlists/" + playlistId + "/tracks";

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("uris", Collections.singletonList(songUri));
        doHttpPostRequest(path, requestBody, String.class);
    }

    @Override
    @HystrixCommand(groupKey = Constants.HystrixGroups.SPOTIFY)
    public CreatePlaylistResponse createPlayList(String name) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("name", name);
        requestBody.put("public", false);

        return doHttpPostRequest("/me/playlists", requestBody, CreatePlaylistResponse.class);
    }

    @Override
    @HystrixCommand(groupKey = Constants.HystrixGroups.SPOTIFY)
    public void deletePlaylist(String id) {
        String path = "/playlists/" + id + "/followers";
        String response = doHttpDeleteRequest(path, null, String.class);
        LOGGER.info(response);
    }
}
