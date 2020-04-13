package com.merpam.onenight.spotify.service;

import com.merpam.onenight.property.DynamicPropertyService;
import com.merpam.onenight.webservice.RestWebService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import java.util.Collections;
import java.util.Map;

public abstract class AbstractSpotifyWebService {

    private static final String SPOTIFY_ACCESS_TOKEN = "spotify.api.access.token";
    private static final String SPOTIFY_BASE_URL = "spotify.api.base.url";

    private Logger LOGGER = LoggerFactory.getLogger(AbstractSpotifyWebService.class);

    private DynamicPropertyService dynamicPropertyService;
    private SpotifyAuthWebService spotifyAuthWebService;

    private RestWebService restWebService;

    @PostConstruct
    public void init() {
        String baseUrl = dynamicPropertyService.getProperty(SPOTIFY_BASE_URL);
        restWebService = new RestWebService(baseUrl);
    }

    protected <T> T doHttpGetRequest(String path, Map<String, Object> queryParams, Class<T> responseClass) {
        try {
            return restWebService.doHttpGetRequest(path, queryParams, createBearerHeaders(), responseClass);
        } catch (NotAuthorizedException | BadRequestException e) {
            LOGGER.info("Getting a new token with refresh token and trying again", e);
            spotifyAuthWebService.refreshToken();
            return restWebService.doHttpGetRequest(path, queryParams, createBearerHeaders(), responseClass);
        }
    }

    protected <T> T doHttpPostRequest(String path, Object requestBody, Class<T> responseClass) {
        try {
            return restWebService.doHttpPostRequest(path,
                    Collections.emptyMap(),
                    createBearerHeaders(),
                    Entity.json(requestBody),
                    responseClass);
        } catch (ClientErrorException | ProcessingException e) {
            LOGGER.info("Getting a new token with refresh token and trying again", e);
            spotifyAuthWebService.refreshToken();
            return restWebService.doHttpPostRequest(path,
                    Collections.emptyMap(),
                    createBearerHeaders(),
                    Entity.json(requestBody),
                    responseClass);
        }
    }

    protected boolean doHttpDeleteRequest(String path, Object requestBody) {
        try {
            return restWebService.doHttpDeleteRequest(path, Collections.emptyMap(), requestBody, createBearerHeaders());
        } catch (ClientErrorException | ProcessingException e) {
            LOGGER.info("Getting a new token with refresh token and trying again", e);
            spotifyAuthWebService.refreshToken();
            return restWebService.doHttpDeleteRequest(path, Collections.emptyMap(), requestBody, createBearerHeaders());
        }
    }

    private MultivaluedMap<String, Object> createBearerHeaders() {
        MultivaluedMap<String, Object> headers = new MultivaluedHashMap<>();
        headers.putSingle("Accept", "application/json");
        headers.putSingle("Content-Type", "application/json");
        headers.putSingle("Authorization", "Bearer " + dynamicPropertyService.getProperty(SPOTIFY_ACCESS_TOKEN));

        return headers;
    }

    @Resource
    public void setDynamicPropertyService(DynamicPropertyService dynamicPropertyService) {
        this.dynamicPropertyService = dynamicPropertyService;
    }

    @Resource
    public void setSpotifyAuthWebService(SpotifyAuthWebService spotifyAuthWebService) {
        this.spotifyAuthWebService = spotifyAuthWebService;
    }
}
