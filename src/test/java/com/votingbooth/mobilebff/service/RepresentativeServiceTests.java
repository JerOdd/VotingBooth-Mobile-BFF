package com.votingbooth.mobilebff.service;

import com.votingbooth.mobilebff.dataaccess.client.representative.RepresentativeClient;
import com.votingbooth.mobilebff.model.dto.RepresentativeResponse;
import com.votingbooth.mobilebff.model.dto.RepresentativeSummaryDto;
import com.votingbooth.mobilebff.model.dto.RepresentativesResponse;
import com.votingbooth.mobilebff.model.exception.RepresentativeNotFoundException;
import com.votingbooth.mobilebff.model.exception.UpstreamUnavailableException;
import com.votingbooth.mobilebff.model.representative.Representative;
import com.votingbooth.mobilebff.model.representative.Representatives;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RepresentativeServiceTests {

    private RepresentativeClient representativeClient;
    private RepresentativeService representativeService;

    @BeforeEach
    void setUp() {
        representativeClient = mock(RepresentativeClient.class);
        representativeService = new RepresentativeService(representativeClient);
    }

    @Test
    void getRepresentatives() {
        UUID id = UUID.randomUUID();
        when(representativeClient.getRepresentatives()).thenReturn(new Representatives(List.of(
                new Representatives.Summary(id, "Mme", "Audrey", "Abadie-Amiel",
                        "https://www.assemblee-nationale.fr/dyn/static/tribun/17/photos/carre/793218.jpg",
                        "Libertés, Indépendants, Outre-mer et Territoires", "LIOT", "#FFD96F", "09", "Ariège", 2))));

        RepresentativesResponse response = representativeService.getRepresentatives();

        assertEquals(List.of(new RepresentativeSummaryDto(id, "Mme", "Audrey", "Abadie-Amiel",
                "https://www.assemblee-nationale.fr/dyn/static/tribun/17/photos/carre/793218.jpg",
                "Libertés, Indépendants, Outre-mer et Territoires", "LIOT", "#FFD96F", "09", "Ariège", 2)),
                response.representatives());
    }

    @Test
    void getRepresentative() {
        UUID id = UUID.randomUUID();
        when(representativeClient.getRepresentative(id)).thenReturn(new Representative(id, "Mme", "Jane", "Doe"));

        RepresentativeResponse response = representativeService.getRepresentative(id);

        assertEquals(new RepresentativeResponse(id, "Mme", "Jane", "Doe"), response);
    }

    @Test
    void representativeNotFound() {
        UUID id = UUID.randomUUID();
        when(representativeClient.getRepresentative(id)).thenThrow(
                HttpClientErrorException.create(HttpStatus.NOT_FOUND, "Not Found", HttpHeaders.EMPTY, null, null));

        assertThrows(RepresentativeNotFoundException.class, () -> representativeService.getRepresentative(id));
    }

    @Test
    void representativeApiUnavailable() {
        when(representativeClient.getRepresentatives()).thenThrow(new ResourceAccessException("Connection refused"));

        assertThrows(UpstreamUnavailableException.class, () -> representativeService.getRepresentatives());
    }

}
