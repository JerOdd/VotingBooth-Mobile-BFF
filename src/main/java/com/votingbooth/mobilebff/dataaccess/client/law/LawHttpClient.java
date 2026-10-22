package com.votingbooth.mobilebff.dataaccess.client.law;

import com.votingbooth.mobilebff.model.law.LawPage;
import com.votingbooth.mobilebff.model.law.LawStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Optional;

@Component
public class LawHttpClient implements LawClient {

    private final RestClient restClient;

    public LawHttpClient(RestClient.Builder restClientBuilder, @Value("${clients.law.base-url}") String baseUrl) {
        this.restClient = restClientBuilder.baseUrl(baseUrl).build();
    }

    @Override
    public LawPage getLaws(int page, int size, LawStatus status, String category) {
        return restClient.get()
                .uri(uri -> uri.path("/laws")
                        .queryParam("page", page)
                        .queryParam("size", size)
                        .queryParamIfPresent("status", Optional.ofNullable(status))
                        .queryParamIfPresent("category", Optional.ofNullable(category))
                        .build())
                .retrieve()
                .body(LawPage.class);
    }

}
