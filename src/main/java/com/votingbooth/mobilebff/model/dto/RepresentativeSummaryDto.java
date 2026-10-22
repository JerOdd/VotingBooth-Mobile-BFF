package com.votingbooth.mobilebff.model.dto;

import java.util.UUID;

public record RepresentativeSummaryDto(UUID id, String civility, String firstName, String lastName,
                                       String photoUrl, String groupName, String groupShortName, String groupColor,
                                       String departmentCode, String departmentName, Integer constituencyNumber) {
}
