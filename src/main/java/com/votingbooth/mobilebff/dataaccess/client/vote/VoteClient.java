package com.votingbooth.mobilebff.dataaccess.client.vote;

import com.votingbooth.mobilebff.model.vote.VoteResult;

import java.util.UUID;

public interface VoteClient {
    VoteResult getVoteResult(UUID lawId);
}
