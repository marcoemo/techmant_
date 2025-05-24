package com.example.GestionSolicitudes.client;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class AsignacionClient {

    private final WebClient webClient;

    public AsignacionClient(@Value("${asignacion-service.url}") String asignacionUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(asignacionUrl)
                .build();
    }

    public Map<String, Object> obtenerAsigancionPorId(Long id) {
        return this.webClient.get()
                .uri("/asignacion/{id}", id)
                .retrieve()
                .bodyToMono(Map.class)
                .block();
    }
}
