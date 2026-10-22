package com.votingbooth.mobilebff.service;

import com.votingbooth.mobilebff.dataaccess.client.law.LawClient;
import com.votingbooth.mobilebff.model.dto.LawSummaryDto;
import com.votingbooth.mobilebff.model.dto.LawsResponse;
import com.votingbooth.mobilebff.model.exception.InvalidPageException;
import com.votingbooth.mobilebff.model.exception.UpstreamUnavailableException;
import com.votingbooth.mobilebff.model.law.InitiatorType;
import com.votingbooth.mobilebff.model.law.LawPage;
import com.votingbooth.mobilebff.model.law.LawStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LawServiceTests {

    private LawClient lawClient;
    private LawService lawService;

    @BeforeEach
    void setUp() {
        lawClient = mock(LawClient.class);
        lawService = new LawService(lawClient);
    }

    @Test
    void getLaws() {
        UUID id = UUID.randomUUID();
        LawPage.Law law = new LawPage.Law(id, "DLR5L17N51234", "Projet de loi de finances pour 2026", "Budget 2026",
                17, InitiatorType.GOVERNMENT, LawStatus.IN_PLENARY, "1ère lecture",
                LocalDate.of(2026, 10, 14), LocalDate.of(2026, 10, 28), null, List.of("Économie"));
        when(lawClient.getLaws(0, 20, LawStatus.IN_PLENARY, "Économie"))
                .thenReturn(new LawPage(List.of(law), 0, 20, 1, 1));

        LawsResponse response = lawService.getLaws(0, 20, LawStatus.IN_PLENARY, "Économie");

        assertEquals(new LawsResponse(List.of(new LawSummaryDto(id, "Projet de loi de finances pour 2026", "Budget 2026",
                InitiatorType.GOVERNMENT, LawStatus.IN_PLENARY, "1ère lecture",
                LocalDate.of(2026, 10, 14), LocalDate.of(2026, 10, 28), null, List.of("Économie"))), 0, 20, 1, 1),
                response);
    }

    @Test
    void invalidPage() {
        when(lawClient.getLaws(-1, 20, null, null)).thenThrow(
                HttpClientErrorException.create(HttpStatus.BAD_REQUEST, "Bad Request", HttpHeaders.EMPTY, null, null));

        assertThrows(InvalidPageException.class, () -> lawService.getLaws(-1, 20, null, null));
    }

    @Test
    void lawApiUnavailable() {
        when(lawClient.getLaws(0, 20, null, null)).thenThrow(new ResourceAccessException("Connection refused"));

        assertThrows(UpstreamUnavailableException.class, () -> lawService.getLaws(0, 20, null, null));
    }

}
