package com.merpam.onenight.webservice;

import org.glassfish.jersey.client.ClientProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.util.Map;

public class RestWebService {

    private static final Logger LOG = LoggerFactory.getLogger(RestWebService.class);
    private String baseRequestUrl;

    public RestWebService(String baseRequestUrl) {
        this.baseRequestUrl = baseRequestUrl;
    }

    public <T> T doHttpGetRequest(String path,
                                  Map<String, Object> queryParams,
                                  MultivaluedMap<String, Object> headers,
                                  Class<T> responseClass) {
        Response response = createWebTargetWithBaseRequestURLAndQueryParams(path, queryParams).request(MediaType.APPLICATION_JSON)
                .headers(headers)
                .get();
        processResponseStatusInfo(response);
        return response.readEntity(responseClass);
    }

    public <T> T doHttpPostRequest(String path,
                                   Map<String, Object> queryParams,
                                   MultivaluedMap<String, Object> headers,
                                   Entity<?> postEntity,
                                   Class<T> responseClass) {
        Response response = createWebTargetWithBaseRequestURLAndQueryParams(path, queryParams)
                .request(MediaType.APPLICATION_JSON)
                .headers(headers)
                .post(postEntity);
        processResponseStatusInfo(response);
        return response.readEntity(responseClass);

    }

    public <T> T doHttpDeleteRequest(String path,
                                     Map<String, Object> queryParams,
                                     Object requestBody,
                                     MultivaluedMap<String, Object> headers,
                                     Class<T> responseClass) {
        return createWebTargetWithBaseRequestURLAndQueryParams(path, queryParams)
                .property(ClientProperties.SUPPRESS_HTTP_COMPLIANCE_VALIDATION, true)
                .request(MediaType.APPLICATION_JSON)
                .headers(headers)
                .method("DELETE", Entity.json(requestBody), responseClass);
    }

    private WebTarget createWebTargetWithBaseRequestURLAndQueryParams(String path, Map<String, Object> queryParams) {
        WebTarget webTarget = createWebTargetWithBaseRequestURL(path);
        for (Map.Entry<String, Object> entry : queryParams.entrySet()) {
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

    private void processResponseStatusInfo(Response response) {
        Response.StatusType statusType = response.getStatusInfo();
        if (statusType.getFamily() != Response.Status.Family.SUCCESSFUL) {
            LOG.info("Unusual status info");
            LOG.info("Reason: {} Code: {}", statusType.getReasonPhrase(), statusType.getStatusCode());
            LOG.info(response.readEntity(String.class));
            throw new ClientErrorException(statusType.toEnum());
        }
    }
}
