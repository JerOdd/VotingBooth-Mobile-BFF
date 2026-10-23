package com.votingbooth.mobilebff.service;

import com.votingbooth.mobilebff.dataaccess.client.vote.VoteClient;
import com.votingbooth.mobilebff.model.dto.VoteResultResponse;
import com.votingbooth.mobilebff.model.exception.UpstreamUnavailableException;
import com.votingbooth.mobilebff.model.vote.VoteResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class VoteServiceTests {

    private VoteClient voteClient;
    private VoteService voteService;

    private final UUID lawId = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        voteClient = mock(VoteClient.class);
        voteService = new VoteService(voteClient);
    }

    @Test
    void getVoteResult() {
        LocalDate voteDate = LocalDate.of(2025, 10, 30);
        when(voteClient.getVoteResult(lawId)).thenReturn(new VoteResult(lawId,
                new VoteResult.RepresentativeResult(voteDate, VoteResult.Outcome.ADOPTED, 185, 184, 5, 14, false),
                new VoteResult.UserResult(12, 5, 3)));

        VoteResultResponse response = voteService.getVoteResult(lawId);

        assertEquals(new VoteResultResponse(lawId,
                new VoteResultResponse.RepresentativeResult(voteDate, VoteResult.Outcome.ADOPTED, 185, 184, 5, 14, false),
                new VoteResultResponse.UserResult(12, 5, 3, 20)), response);
    }

    @Test
    void lawPassedBy493() {
        when(voteClient.getVoteResult(lawId)).thenReturn(new VoteResult(lawId,
                new VoteResult.RepresentativeResult(LocalDate.of(2025, 2, 5), VoteResult.Outcome.ADOPTED, 122, 0, 0, 1, true),
                null));

        VoteResultResponse.RepresentativeResult result = voteService.getVoteResult(lawId).representativeResult();

        assertTrue(result.decidedByCensureMotion());
        assertEquals(VoteResult.Outcome.ADOPTED, result.result());
    }

    @Test
    void noResultsYet() {
        when(voteClient.getVoteResult(lawId)).thenReturn(new VoteResult(lawId, null, null));

        assertEquals(new VoteResultResponse(lawId, null, null), voteService.getVoteResult(lawId));
    }

    @Test
    void voteApiUnavailable() {
        when(voteClient.getVoteResult(lawId)).thenThrow(new ResourceAccessException("Connection refused"));

        assertThrows(UpstreamUnavailableException.class, () -> voteService.getVoteResult(lawId));
    }

    @Test
    void voteApiFails() {
        when(voteClient.getVoteResult(lawId)).thenThrow(
                HttpServerErrorException.create(HttpStatus.INTERNAL_SERVER_ERROR, "Error", HttpHeaders.EMPTY, null, null));

        assertThrows(UpstreamUnavailableException.class, () -> voteService.getVoteResult(lawId));
    }

}
