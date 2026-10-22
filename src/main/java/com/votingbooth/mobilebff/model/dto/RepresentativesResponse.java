package com.votingbooth.mobilebff.model.dto;

import java.util.List;

public record RepresentativesResponse(List<RepresentativeSummaryDto> representatives) {
}
