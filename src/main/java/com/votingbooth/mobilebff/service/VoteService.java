package com.votingbooth.mobilebff.service;

import com.votingbooth.mobilebff.dataaccess.client.vote.VoteClient;
import com.votingbooth.mobilebff.model.dto.VoteResultResponse;
import com.votingbooth.mobilebff.model.exception.UpstreamUnavailableException;
import com.votingbooth.mobilebff.model.vote.VoteResult;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import java.util.UUID;

@Service
public class VoteService {

    private final VoteClient voteClient;

    public VoteService(VoteClient voteClient) {
        this.voteClient = voteClient;
    }

    public VoteResultResponse getVoteResult(UUID lawId) {
        VoteResult result;
        try {
            result = voteClient.getVoteResult(lawId);
        } catch (RestClientException e) {
            throw new UpstreamUnavailableException("vote-api", e);
        }

        return new VoteResultResponse(lawId, toDto(result.representativeResult()), toDto(result.userResult()));
    }

    private static VoteResultResponse.RepresentativeResult toDto(VoteResult.RepresentativeResult result) {
        if (result == null) {
            return null;
        }
        return new VoteResultResponse.RepresentativeResult(result.voteDate(), result.result(), result.yes(), result.no(),
                result.abstain(), result.nonVoting(), result.decidedByCensureMotion());
    }

    private static VoteResultResponse.UserResult toDto(VoteResult.UserResult result) {
        if (result == null) {
            return null;
        }
        return new VoteResultResponse.UserResult(result.yes(), result.no(), result.abstain(),
                result.yes() + result.no() + result.abstain());
    }

}
