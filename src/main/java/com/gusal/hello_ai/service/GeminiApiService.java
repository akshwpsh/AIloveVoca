package com.gusal.hello_ai.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import com.fasterxml.jackson.databind.JsonNode;

@Service
public class GeminiApiService {
    private final WebClient webClient;

    @Value("${gemini.api.key}")
    private String apiKey;

    public GeminiApiService() {
        this.webClient = WebClient.builder()
                .baseUrl("https://generativelanguage.googleapis.com/v1beta")
                .defaultHeader("Content-Type", "application/json")
                .build();
    }

    public Mono<JsonNode> generateContent(String prompt) {
        String url = String.format("/models/gemini-1.5-flash:generateContent?key=%s", apiKey);

        // Request body construction
        String requestBody = String.format(
                "{ \"contents\": [ { \"parts\": [ { \"text\": \"%s\" } ] } ] }",
                prompt
        );

        return webClient.post()
                .uri(url)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(JsonNode.class)
                .doOnError(error -> System.err.println("Error occurred: " + error.getMessage()));
    }
}
