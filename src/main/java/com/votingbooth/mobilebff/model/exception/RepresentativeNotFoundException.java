package com.votingbooth.mobilebff.model.exception;

import java.util.UUID;

public class RepresentativeNotFoundException extends RuntimeException {

    public RepresentativeNotFoundException(UUID representativeId) {
        super("Representative " + representativeId + " not found.");
    }

}
