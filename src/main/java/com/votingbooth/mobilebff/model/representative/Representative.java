package com.votingbooth.mobilebff.model.representative;

import java.util.UUID;

// representative-api's GET /representatives/{id} response.
public record Representative(UUID id, String civility, String firstName, String lastName) {
}
