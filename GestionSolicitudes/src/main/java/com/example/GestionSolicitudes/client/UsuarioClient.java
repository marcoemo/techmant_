package com.example.GestionSolicitudes.client;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class UsuarioClient {
    private final WebClient webClient;

    public UsuarioClient(@Value("${usuario-service.url}") String usuarioUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(usuarioUrl)
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
