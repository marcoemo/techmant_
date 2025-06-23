package com.example.Reportes.client;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.reactive.function.client.WebClient;

public class DiagnosticoClient {

        private final WebClient webClient;

  public DiagnosticoClient(@Value("${diagnostico-service.url}") String baseUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }
   public Map<String, Object> obtenerDiagnosticoId(Long id) {
        return this.webClient.get()
                .uri("/{id}", id)
                .retrieve()
                .onStatus(status -> status.is4xxClientError(),
                    response -> response.bodyToMono(String.class)
                    .map(body -> new RuntimeException("Diagnostico no encontrado")))
                .bodyToMono(Map.class)
                .block();   
    }
}

