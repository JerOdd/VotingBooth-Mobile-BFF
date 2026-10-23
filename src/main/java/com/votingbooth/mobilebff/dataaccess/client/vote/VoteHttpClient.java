package com.votingbooth.mobilebff.dataaccess.client.vote;

import com.votingbooth.mobilebff.model.vote.VoteResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.UUID;

@Component
public class VoteHttpClient implements VoteClient {

    private final RestClient restClient;

    public VoteHttpClient(RestClient.Builder restClientBuilder,
                          @Value("${clients.vote.base-url}") String baseUrl) {
        this.restClient = restClientBuilder.baseUrl(baseUrl).build();
    }

    @Override
    public VoteResult getVoteResult(UUID lawId) {
        return restClient.get()
                .uri("/votes/{lawId}", lawId)
                .retrieve()
                .body(VoteResult.class);
    }

}
