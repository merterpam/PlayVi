package com.merpam.onenight.scheduler;

import com.merpam.onenight.property.DynamicPropertyService;
import com.merpam.onenight.spotify.service.SpotifyAuthWebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class SpotifyRefreshTokenScheduler {

    private static final String SPOTIFY_REFRESH_TOKEN_EXPIRES_IN = "spotify.api.refresh.expires_in";

    private SpotifyAuthWebService spotifyAuthWebService;
    private DynamicPropertyService dynamicPropertyService;

    @PostConstruct
    public void onStartup() {
        refreshToken();
    }

    // TODO make it dynamic
    @Scheduled(fixedRate = 3600*1000)
    public void runSpotifyRefreshTokenScheduler() {
        refreshToken();
    }

    private void refreshToken() {
        spotifyAuthWebService.refreshToken();
    }

    private long getExpiresIn() {

        return Long.parseLong(dynamicPropertyService.getProperty("SPOTIFY_REFRESH_TOKEN_EXPIRES_IN"));
    }

    @Autowired
    public void setSpotifyAuthWebService(SpotifyAuthWebService spotifyAuthWebService) {
        this.spotifyAuthWebService = spotifyAuthWebService;
    }

    @Autowired
    public void setDynamicPropertyService(DynamicPropertyService dynamicPropertyService) {
        this.dynamicPropertyService = dynamicPropertyService;
    }
}
