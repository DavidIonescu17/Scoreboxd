package com.scoreboxd.backend;

import com.scoreboxd.backend.external.dto.FootballFixtureDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FootballApiClient {

    private final WebClient footballWebClient;

    public List<FootballFixtureDto> fetchFixtures(LocalDate date) {
        return footballWebClient
                .get()
                .uri(uriBuilder -> uriBuilder
                     .path("/fixtures")
                     .queryParam("date", date)
                     .build())
                .retrieve()
                .bodyToMono(ApiResponse.class)
                .block()                                        // blocking for simplicity
                .response();
    }

    // helper to match API’s envelope { response: [...] }
    private record ApiResponse(List<FootballFixtureDto> response) {}
}
