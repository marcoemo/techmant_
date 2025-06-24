package com.example.Soporte.client;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class UsuarioClient {

    private final WebClient webClient;

    public UsuarioClient(@Value("${usuario-service.url}") String equipoUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(equipoUrl)
                .build();
    }

    public String obtenerUsuarioPorId(Long id) {
        return this.webClient.get()
                .uri("/usuario/{id}", id)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}