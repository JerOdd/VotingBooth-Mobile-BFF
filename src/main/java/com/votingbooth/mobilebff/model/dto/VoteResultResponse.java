package com.votingbooth.mobilebff.model.dto;

import com.votingbooth.mobilebff.model.vote.VoteResult;

import java.time.LocalDate;
import java.util.UUID;

// A law's results for its page: how the Assemblée decided it and how citizens voted. Either is null when there's none yet.
public record VoteResultResponse(UUID lawId, RepresentativeResult representativeResult, UserResult userResult) {

    // decidedByCensureMotion: a law passed by 49.3, whose counts are the votes for the censure motion.
    public record RepresentativeResult(LocalDate voteDate, VoteResult.Outcome result, long yes, long no, long abstain,
                                       long nonVoting, boolean decidedByCensureMotion) {
    }

    public record UserResult(long yes, long no, long abstain, long total) {
    }

}
