package com.votingbooth.mobilebff.dataaccess.client.representative;

import com.votingbooth.mobilebff.model.representative.Representative;
import com.votingbooth.mobilebff.model.representative.Representatives;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.UUID;

@Component
public class RepresentativeHttpClient implements RepresentativeClient {

    private final RestClient restClient;

    public RepresentativeHttpClient(RestClient.Builder restClientBuilder,
                                    @Value("${clients.representative.base-url}") String baseUrl) {
        this.restClient = restClientBuilder.baseUrl(baseUrl).build();
    }

    @Override
    public Representatives getRepresentatives() {
        return restClient.get()
                .uri("/representatives")
                .retrieve()
                .body(Representatives.class);
    }

    @Override
    public Representative getRepresentative(UUID representativeId) {
        return restClient.get()
                .uri("/representatives/{id}", representativeId)
                .retrieve()
                .body(Representative.class);
    }

}
