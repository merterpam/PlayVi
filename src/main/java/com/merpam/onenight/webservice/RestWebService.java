package com.merpam.onenight.webservice;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import java.util.Map;

public class RestWebService {

    private String baseRequestUrl;

    public RestWebService(String baseRequestUrl) {
        this.baseRequestUrl = baseRequestUrl;
    }

    public <T> T doHttpGetRequest(String path,
                                   Map<String, String> queryParams,
                                   MultivaluedMap<String, Object> headers,
                                  Class<T> responseClass) {
        return createWebTargetWithBaseRequestURLAndQueryParams(path, queryParams).request(MediaType.APPLICATION_JSON)
                .headers(headers)
                .get(responseClass);
    }

    public <T> T doHttpPostRequest(String path,
                                   Map<String, String> queryParams,
                                   MultivaluedMap<String, Object> headers,
                                   MultivaluedMap<String, String> formData,
                                   Class<T> responseClass) {
        return createWebTargetWithBaseRequestURLAndQueryParams(path, queryParams).request(MediaType.APPLICATION_JSON)
                .headers(headers)
                .post(Entity.form(formData))
                .readEntity(responseClass);
    }

    private WebTarget createWebTargetWithBaseRequestURLAndQueryParams(String path, Map<String, String> queryParams) {
        WebTarget webTarget = createWebTargetWithBaseRequestURL(path);
        for (Map.Entry<String, String> entry : queryParams.entrySet()) {
            webTarget = webTarget.queryParam(entry.getKey(), entry.getValue());
        }

        return webTarget;
    }

    private WebTarget createWebTargetWithBaseRequestURL(String path) {
        Client client = ClientBuilder.newClient();
        return buildBaseRequestURL(client).path(path);
    }

    private WebTarget buildBaseRequestURL(Client client) {
        return client.target(baseRequestUrl);
    }
}
