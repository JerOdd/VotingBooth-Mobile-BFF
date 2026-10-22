package com.votingbooth.mobilebff.model.dto;

import com.votingbooth.mobilebff.model.law.InitiatorType;
import com.votingbooth.mobilebff.model.law.LawStatus;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

// shortTitle is null until law-service has synced the initial text: show title instead.
public record LawSummaryDto(
        UUID id,
        String title,
        String shortTitle,
        InitiatorType initiatorType,
        LawStatus status,
        String currentStageLabel,
        LocalDate lastActDate,
        LocalDate nextScheduledDate,
        LocalDate promulgationDate,
        List<String> categories
) {
}
