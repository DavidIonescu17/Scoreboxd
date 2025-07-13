package com.scoreboxd.backend.client;

import com.scoreboxd.backend.dto.FootballFixtureDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import java.time.OffsetDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FootballApiClient {

    private final WebClient footballWebClient;
    private static final Logger log = LoggerFactory.getLogger(FootballApiClient.class);

    @org.springframework.beans.factory.annotation.Value("${app.football-api.host}")
    private String host;

    @org.springframework.beans.factory.annotation.Value("${app.football-api.key}")
    private String apiKey;

    public List<FootballFixtureDto> fetchFixtures(OffsetDateTime date) {
        log.info("Fetching fixtures for date: {}", date);
        String formattedDate = date.toLocalDate().toString();
        try {
            // Build the URI and log it
            String uriString = org.springframework.web.util.UriComponentsBuilder
                .fromHttpUrl("https://" + host + "/fixtures")
                .queryParam("date", formattedDate)
                .build()
                .toUriString();
            log.info("Requesting fixtures from URL: {}", uriString);

            // Get the raw response as String and log it
            String rawBody = footballWebClient
                .get()
                .uri(uriBuilder -> uriBuilder
                    .path("/fixtures")
                    .queryParam("date", formattedDate)
                    .build())
                .header("x-apisports-key", apiKey)
                .retrieve()
                .onStatus(status -> status.isError(), response -> {
                    log.error("API Error: {}", response.statusCode());
                    return response.bodyToMono(String.class)
                            .map(body -> new RuntimeException("API Error: " + body));
                })
                .bodyToMono(String.class)
                .block();
            log.info("Raw API response: {}", rawBody);

            // Now map the rawBody to ApiResponse
            ApiResponse apiResponse = footballWebClient
                .get()
                .uri(uriBuilder -> uriBuilder
                    .path("/fixtures")
                    .queryParam("date", formattedDate)
                    .build())
                .header("x-apisports-key", apiKey)
                .retrieve()
                .bodyToMono(ApiResponse.class)
                    .block(); // blocking for simplicity

            if (apiResponse != null) {
                log.info("API Response: {}", apiResponse.response());
                log.info("Received {} fixtures from API", apiResponse.response().size());
                return apiResponse.response();
            } else {
                log.warn("No fixtures received from API");
                return List.of();
            }
        } catch (Exception e) {
            log.error("Error fetching fixtures: {}", e.getMessage(), e);
            return List.of();
        }
    }

    // helper to match API's envelope { response: [...] }
    private record ApiResponse(List<FootballFixtureDto> response) {}
}