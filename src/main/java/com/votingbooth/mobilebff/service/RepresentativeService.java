package com.votingbooth.mobilebff.service;

import com.votingbooth.mobilebff.dataaccess.client.representative.RepresentativeClient;
import com.votingbooth.mobilebff.model.dto.RepresentativeResponse;
import com.votingbooth.mobilebff.model.dto.RepresentativeSummaryDto;
import com.votingbooth.mobilebff.model.dto.RepresentativesResponse;
import com.votingbooth.mobilebff.model.exception.RepresentativeNotFoundException;
import com.votingbooth.mobilebff.model.exception.UpstreamUnavailableException;
import com.votingbooth.mobilebff.model.representative.Representative;
import com.votingbooth.mobilebff.model.representative.Representatives;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;

import java.util.UUID;

@Service
public class RepresentativeService {

    private final RepresentativeClient representativeClient;

    public RepresentativeService(RepresentativeClient representativeClient) {
        this.representativeClient = representativeClient;
    }

    public RepresentativesResponse getRepresentatives() {
        Representatives representatives;
        try {
            representatives = representativeClient.getRepresentatives();
        } catch (RestClientException e) {
            throw new UpstreamUnavailableException("representative-api", e);
        }

        return new RepresentativesResponse(
                representatives.representatives().stream().map(RepresentativeService::toSummary).toList()
        );
    }

    public RepresentativeResponse getRepresentative(UUID representativeId) {
        Representative representative;
        try {
            representative = representativeClient.getRepresentative(representativeId);
        } catch (HttpClientErrorException.NotFound e) {
            throw new RepresentativeNotFoundException(representativeId);
        } catch (RestClientException e) {
            throw new UpstreamUnavailableException("representative-api", e);
        }

        return new RepresentativeResponse(
                representative.id(),
                representative.civility(),
                representative.firstName(),
                representative.lastName()
        );
    }

    private static RepresentativeSummaryDto toSummary(Representatives.Summary summary) {
        return new RepresentativeSummaryDto(
                summary.id(),
                summary.civility(),
                summary.firstName(),
                summary.lastName(),
                summary.photoUrl(),
                summary.groupName(),
                summary.groupShortName(),
                summary.groupColor(),
                summary.departmentCode(),
                summary.departmentName(),
                summary.constituencyNumber()
        );
    }

}
