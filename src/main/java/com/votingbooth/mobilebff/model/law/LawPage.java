package com.votingbooth.mobilebff.model.law;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

// law-api's GET /laws response.
public record LawPage(List<Law> laws, int page, int size, long totalElements, int totalPages) {

    public record Law(
            UUID id,
            String externalId,
            String title,
            String shortTitle,
            int legislature,
            InitiatorType initiatorType,
            LawStatus status,
            String currentStageLabel,
            LocalDate lastActDate,
            LocalDate nextScheduledDate,
            LocalDate promulgationDate,
            List<String> categories
    ) {
    }

}
