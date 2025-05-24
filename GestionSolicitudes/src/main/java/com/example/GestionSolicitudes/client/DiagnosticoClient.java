package com.example.GestionSolicitudes.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Component
public class DiagnosticoClient {

    private final WebClient webClient;

    public DiagnosticoClient(@Value("${diagnostico-service.url}") String diagnosticoUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(diagnosticoUrl)
                .build();
    }

    public Map<String, Object> obtenerDiagnosticoPorId(Long id) {
        return this.webClient.get()
                .uri("/diagnosticos/{id}", id)
                .retrieve()
                .bodyToMono(Map.class)
                .block();
    }
}