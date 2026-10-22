package com.votingbooth.mobilebff.model.representative;

import java.util.List;
import java.util.UUID;

// representative-api's GET /representatives response.
public record Representatives(List<Summary> representatives) {

    public record Summary(UUID id, String civility, String firstName, String lastName,
                          String photoUrl, String groupName, String groupShortName, String groupColor,
                          String departmentCode, String departmentName, Integer constituencyNumber) {
    }

}
