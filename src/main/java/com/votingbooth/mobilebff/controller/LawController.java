package com.votingbooth.mobilebff.controller;

import com.votingbooth.mobilebff.model.dto.LawsResponse;
import com.votingbooth.mobilebff.model.law.LawStatus;
import com.votingbooth.mobilebff.service.LawService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/laws")
public class LawController {

    private final LawService lawService;

    public LawController(LawService lawService) {
        this.lawService = lawService;
    }

    @GetMapping
    public ResponseEntity<LawsResponse> getLaws(@RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "20") int size,
                                                @RequestParam(required = false) LawStatus status,
                                                @RequestParam(required = false) String category) {
        return ResponseEntity.ok(lawService.getLaws(page, size, status, category));
    }

}
