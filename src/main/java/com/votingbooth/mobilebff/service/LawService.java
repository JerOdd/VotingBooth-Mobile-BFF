package com.votingbooth.mobilebff.service;

import com.votingbooth.mobilebff.dataaccess.client.law.LawClient;
import com.votingbooth.mobilebff.model.dto.LawSummaryDto;
import com.votingbooth.mobilebff.model.dto.LawsResponse;
import com.votingbooth.mobilebff.model.exception.InvalidPageException;
import com.votingbooth.mobilebff.model.exception.UpstreamUnavailableException;
import com.votingbooth.mobilebff.model.law.LawPage;
import com.votingbooth.mobilebff.model.law.LawStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;

@Service
public class LawService {

    private final LawClient lawClient;

    public LawService(LawClient lawClient) {
        this.lawClient = lawClient;
    }

    public LawsResponse getLaws(int page, int size, LawStatus status, String category) {
        LawPage lawPage;
        try {
            lawPage = lawClient.getLaws(page, size, status, category);
        } catch (HttpClientErrorException.BadRequest e) {
            throw new InvalidPageException(page, size);
        } catch (RestClientException e) {
            throw new UpstreamUnavailableException("law-api", e);
        }

        return new LawsResponse(
                lawPage.laws().stream().map(LawService::toSummary).toList(),
                lawPage.page(),
                lawPage.size(),
                lawPage.totalElements(),
                lawPage.totalPages()
        );
    }

    private static LawSummaryDto toSummary(LawPage.Law law) {
        return new LawSummaryDto(
                law.id(),
                law.title(),
                law.shortTitle(),
                law.initiatorType(),
                law.status(),
                law.currentStageLabel(),
                law.lastActDate(),
                law.nextScheduledDate(),
                law.promulgationDate(),
                law.categories()
        );
    }

}
