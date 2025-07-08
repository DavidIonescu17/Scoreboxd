package com.scoreboxd.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    WebClient footballWebClient(
            @Value("${app.football-api.host}") String host,
            @Value("${app.football-api.key}") String apiKey) {
        return WebClient.builder()
                .baseUrl("https://" + host)
                .defaultHeader("x-apisports-key", apiKey)
                .build();
    }
}