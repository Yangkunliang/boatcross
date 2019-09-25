package com.kelystor.boatcross.vendor.gitlab;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kelystor.boatcross.vendor.exception.ApiRequestException;
import com.kelystor.boatcross.vendor.gitlab.model.GitLabMergeRequest;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class GitLabAPI {
    private static final Logger LOGGER = LoggerFactory.getLogger(GitLabAPI.class);
    private static final ObjectMapper MAPPER = new ObjectMapper().setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE).configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).registerModule(new JavaTimeModule());
    private static final String DEFAULT_API_NAMESPACE = "/api/v4";

    private String hostUrl;
    private String apiToken;

    private GitLabAPI(String hostUrl, String apiToken) {
        this.hostUrl = hostUrl;
        this.apiToken = apiToken;
    }

    public static GitLabAPI connect(String hostUrl, String apiToken) {
        return new GitLabAPI(hostUrl, apiToken);
    }

    public GitLabMergeRequest getProjectLastMergeRequest(String projectId) {
        HttpGet get = new HttpGet(hostUrl + DEFAULT_API_NAMESPACE + "/projects/" + sanitizeProjectId(projectId) + "/merge_requests?state=opened&state=merged&scope=all&order_by=created_at&sort=desc&page=1&per_page=1");
        String content = executeRequest(get);
        try {
            return MAPPER.convertValue(MAPPER.readTree(content).get(0), GitLabMergeRequest.class);
        } catch (IOException ignore) {
            return null;
        }
    }

    private String executeRequest(HttpUriRequest request) {
        try (CloseableHttpClient client = HttpClientBuilder.create().build()) {
            request.addHeader("Private-Token", apiToken);
            try (CloseableHttpResponse response = client.execute(request)) {
                HttpEntity entity = response.getEntity();
                return EntityUtils.toString(entity, StandardCharsets.UTF_8);
            }
        } catch (IOException ignore) {
            return null;
        }
    }

    private String sanitizeProjectId(String projectId) {
        try {
            return URLEncoder.encode(projectId, StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            throw new ApiRequestException("GitLab项目名称 " + projectId + " URLEncoder.encode失败");
        }
    }
}
