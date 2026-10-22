package com.votingbooth.mobilebff.model.law;

// Mirrors law-api's LawStatus.
public enum LawStatus {
    DEPOSITED,
    IN_COMMITTEE,
    IN_PLENARY,
    IN_SENATE,
    CMP,
    NEW_READING,
    ADOPTED,
    CONSTITUTIONAL_COUNCIL,
    PROMULGATED,
    REJECTED,
    WITHDRAWN,
    LAPSED,
    STALLED
}
