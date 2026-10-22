package com.votingbooth.mobilebff.controller;

import com.votingbooth.mobilebff.model.dto.RepresentativeResponse;
import com.votingbooth.mobilebff.model.dto.RepresentativesResponse;
import com.votingbooth.mobilebff.service.RepresentativeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/{version}/representatives")
public class RepresentativeController {

    private final RepresentativeService representativeService;

    public RepresentativeController(RepresentativeService representativeService) {
        this.representativeService = representativeService;
    }

    @GetMapping(version = "1+")
    public ResponseEntity<RepresentativesResponse> getRepresentatives() {
        return ResponseEntity.ok(representativeService.getRepresentatives());
    }

    @GetMapping(path = "/{id}", version = "1+")
    public ResponseEntity<RepresentativeResponse> getRepresentative(@PathVariable UUID id) {
        return ResponseEntity.ok(representativeService.getRepresentative(id));
    }

}
