package com.votingbooth.mobilebff.dataaccess.client.representative;

import com.votingbooth.mobilebff.model.representative.Representative;
import com.votingbooth.mobilebff.model.representative.Representatives;

import java.util.UUID;

public interface RepresentativeClient {
    Representatives getRepresentatives();

    Representative getRepresentative(UUID representativeId);
}
