package com.merpam.onenight.spotify.impl;

import com.merpam.onenight.constants.Constants;
import com.merpam.onenight.spotify.AbstractSpotifyWebService;
import com.merpam.onenight.spotify.SpotifyWebService;
import com.merpam.onenight.spotify.pojo.TracksResponse;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SpotifyWebServiceImpl extends AbstractSpotifyWebService implements SpotifyWebService {

    @Override
    @HystrixCommand(groupKey = Constants.HystrixGroups.SPOTIFY)
    public TracksResponse getSongs(String query) {
        Map<String, String> queryParam = new HashMap<>();
        queryParam.put("q", query);
        queryParam.put("type", "track");

        return doHttpGetRequest("/search", queryParam, TracksResponse.class);
    }
}
