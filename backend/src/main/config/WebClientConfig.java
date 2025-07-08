// src/main/java/com/scoreboxd/backend/config/WebClientConfig.java
package com.scoreboxd.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient footballWebClient(
            @Value("${FOOTBALL_API_HOST}") String host,
            @Value("${FOOTBALL_API_KEY}") String apiKey) {

        return WebClient.builder()
                .baseUrl("https://" + host)
                .defaultHeader("x-apisports-key", apiKey)
                .build();
    }
}
