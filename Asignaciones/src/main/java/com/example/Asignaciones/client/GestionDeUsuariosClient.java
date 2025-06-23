package com.example.Asignaciones.client;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class GestionDeUsuariosClient {

    private final WebClient webClient;

    public GestionDeUsuariosClient(@Value("${usuario-service.url}") String asignacionUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(asignacionUrl)
                .build();
    }

    public Map<String, Object> obtenerUsuarioPorId(Long id) {
        return this.webClient.get()
                .uri("/usuario/{id}", id)
                .retrieve()
                .bodyToMono(Map.class)
                .block();
    }
}