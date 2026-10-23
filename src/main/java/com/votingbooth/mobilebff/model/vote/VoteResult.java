package com.votingbooth.mobilebff.model.vote;

import java.time.LocalDate;
import java.util.UUID;

// vote-api's GET /votes/{lawId} response. representativeResult is null until the Assemblée decided the law, userResult
// while too few citizens voted on it.
public record VoteResult(UUID lawId, RepresentativeResult representativeResult, UserResult userResult) {

    // For a law passed by 49.3, the counts are the censure motion's and result is the law's.
    public record RepresentativeResult(LocalDate voteDate, Outcome result, long yes, long no, long abstain, long nonVoting,
                                       boolean decidedByCensureMotion) {
    }

    public record UserResult(long yes, long no, long abstain) {
    }

    public enum Outcome {
        ADOPTED,
        REJECTED
    }

}
