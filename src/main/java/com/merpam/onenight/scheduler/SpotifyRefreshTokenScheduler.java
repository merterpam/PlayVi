package com.merpam.onenight.scheduler;

import com.merpam.onenight.configuration.ConfigurationService;
import com.merpam.onenight.spotify.service.SpotifyAuthWebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class SpotifyRefreshTokenScheduler {

    private static final String SPOTIFY_REFRESH_TOKEN_EXPIRES_IN = "spotify.api.refresh.expires_in";

    private SpotifyAuthWebService spotifyAuthWebService;
    private ConfigurationService configurationService;

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

        return Long.parseLong(configurationService.getProperty("SPOTIFY_REFRESH_TOKEN_EXPIRES_IN"));
    }

    @Autowired
    public void setSpotifyAuthWebService(SpotifyAuthWebService spotifyAuthWebService) {
        this.spotifyAuthWebService = spotifyAuthWebService;
    }

    @Autowired
    public void setConfigurationService(ConfigurationService configurationService) {
        this.configurationService = configurationService;
    }
}
