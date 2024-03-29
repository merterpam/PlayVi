package com.merpam.onenight.spotify.service.impl;

import com.merpam.onenight.configuration.ConfigurationService;
import com.merpam.onenight.constants.Constants;
import com.merpam.onenight.spotify.service.SpotifyAuthWebService;
import com.merpam.onenight.spotify.service.model.RefreshTokenResponse;
import com.merpam.onenight.webservice.RestWebService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.glassfish.jersey.internal.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import java.util.Collections;

@Service
public class SpotifyAuthWebServiceImpl implements SpotifyAuthWebService {

    private static final String SPOTIFY_ACCOUNT_TOKEN_URL = "spotify.api.accont.token.url";
    private static final String SPOTIFY_CLIENT_ID = "spotify.api.client.id";
    private static final String SPOTIFY_CLIENT_SECRET = "spotify.api.client.secret";
    private static final String SPOTIFY_REFRESH_TOKEN = "spotify.api.refresh.token";
    private static final String SPOTIFY_ACCESS_TOKEN = "spotify.api.access.token";
    private static final String SPOTIFY_TOKEN_TYPE = "spotify.api.token.type";


    private ConfigurationService configurationService;
    private RestWebService restWebService;


    @Autowired
    public SpotifyAuthWebServiceImpl(ConfigurationService configurationService) {
        this.configurationService = configurationService;

        String baseRequestUrl = this.configurationService.getProperty(SPOTIFY_ACCOUNT_TOKEN_URL);
        this.restWebService = new RestWebService(baseRequestUrl);
    }

    @Override
    @HystrixCommand(groupKey = Constants.HystrixGroups.SPOTIFY)
    public void refreshToken() {
        MultivaluedMap<String, String> formData = new MultivaluedHashMap<>();
        formData.putSingle("grant_type", "refresh_token");
        formData.putSingle("refresh_token", configurationService.getProperty(SPOTIFY_REFRESH_TOKEN));

        RefreshTokenResponse response = restWebService.doHttpPostRequest("/token",
                Collections.emptyMap(),
                createBasicAuthHeaders(),
                Entity.form(formData),
                RefreshTokenResponse.class);

        configurationService.setProperty(SPOTIFY_ACCESS_TOKEN, response.getAccess_token());
        configurationService.setProperty(SPOTIFY_TOKEN_TYPE, response.getToken_type());
    }

    private MultivaluedMap<String, Object> createBasicAuthHeaders() {
        MultivaluedMap<String, Object> headers = new MultivaluedHashMap<>();
        headers.putSingle("Authorization", "Basic " + getEncodedClientInformation());

        return headers;
    }

    private String getEncodedClientInformation() {
        String clientId = configurationService.getProperty(SPOTIFY_CLIENT_ID);
        String clientSecret = configurationService.getProperty(SPOTIFY_CLIENT_SECRET);
        return Base64.encodeAsString(clientId + ":" + clientSecret);
    }

}
