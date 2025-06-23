package com.example.Asignaciones.client;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;

@Component
public class GestionSolicitudesClient {
    private final WebClient webClient;

    public GestionSolicitudesClient(@Value("${solicitud-service.url}") String solicitudUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(solicitudUrl)
                .build();
    }

    public Map<String, Object> obtenerSolicitudPorId(Long id) {
        return this.webClient.get()
                .uri("/solicitud/{id}", id)
                .retrieve()
                .bodyToMono(Map.class)
                .block();
    }
}