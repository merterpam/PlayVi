package com.merpam.onenight.spotify;

import com.merpam.onenight.configuration.ConfigurationService;
import com.merpam.onenight.webservice.RestWebService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import java.util.Map;

public abstract class AbstractSpotifyWebService {

    private static final String SPOTIFY_ACCESS_TOKEN = "spotify.api.access.token";
    private static final String SPOTIFY_BASE_URL = "spotify.api.base.url";

    private Logger LOGGER = LoggerFactory.getLogger(AbstractSpotifyWebService.class);

    private ConfigurationService configurationService;
    private SpotifyAuthWebService spotifyAuthWebService;

    private RestWebService restWebService;

    @PostConstruct
    public void init() {
        String baseUrl = configurationService.getProperty(SPOTIFY_BASE_URL);
        restWebService = new RestWebService(baseUrl);
    }

    protected <T> T doHttpGetRequest(String path, Map<String, String> queryParams, Class<T> responseClass) {
        try {
            return restWebService.doHttpGetRequest(path, queryParams, createBearerHeaders(), responseClass);
        } catch (NotAuthorizedException | BadRequestException e) {
            LOGGER.info("Gtting a new token with refresh token and trying again", e);
            spotifyAuthWebService.refreshToken();
            return restWebService.doHttpGetRequest(path, queryParams, createBearerHeaders(), responseClass);
        }
    }

    private MultivaluedMap<String, Object> createBearerHeaders() {
        MultivaluedMap<String, Object> headers = new MultivaluedHashMap<>();
        headers.putSingle("Accept", "application/json");
        headers.putSingle("Content-Type", "application/json");
        headers.putSingle("Authorization", "Bearer " + configurationService.getProperty(SPOTIFY_ACCESS_TOKEN));

        return headers;
    }

    @Resource
    public void setConfigurationService(ConfigurationService configurationService) {
        this.configurationService = configurationService;
    }

    @Resource
    public void setSpotifyAuthWebService(SpotifyAuthWebService spotifyAuthWebService) {
        this.spotifyAuthWebService = spotifyAuthWebService;
    }
}
