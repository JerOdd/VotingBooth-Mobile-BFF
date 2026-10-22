package com.votingbooth.mobilebff.model.exception;

// A downstream VotingBooth service failed or could not be reached.
public class UpstreamUnavailableException extends RuntimeException {

    public UpstreamUnavailableException(String upstream, Throwable cause) {
        super(upstream + " is unavailable.", cause);
    }

}
