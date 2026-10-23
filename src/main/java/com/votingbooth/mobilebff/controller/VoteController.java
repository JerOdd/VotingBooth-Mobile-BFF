package com.votingbooth.mobilebff.controller;

import com.votingbooth.mobilebff.model.dto.VoteResultResponse;
import com.votingbooth.mobilebff.service.VoteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/{version}/votes")
public class VoteController {

    private final VoteService voteService;

    public VoteController(VoteService voteService) {
        this.voteService = voteService;
    }

    @GetMapping(path = "/{lawId}", version = "1+")
    public ResponseEntity<VoteResultResponse> getVoteResult(@PathVariable UUID lawId) {
        return ResponseEntity.ok(voteService.getVoteResult(lawId));
    }

}
