package com.scoreboxd.backend;

import com.scoreboxd.backend.dto.FootballFixtureDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.OffsetDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FootballApiClient {

    private final WebClient footballWebClient;

    public List<FootballFixtureDto> fetchFixtures(OffsetDateTime date) {
        ApiResponse apiResponse = footballWebClient
                .get()
                .uri(uriBuilder -> uriBuilder
                     .path("/fixtures")
                     .queryParam("date", date)
                     .build())
                .retrieve()
                .bodyToMono(ApiResponse.class)
                .block(); // blocking for simplicity

        return apiResponse != null ? apiResponse.response() : List.of();
    }

    // helper to match API’s envelope { response: [...] }
    private record ApiResponse(List<FootballFixtureDto> response) {}
}