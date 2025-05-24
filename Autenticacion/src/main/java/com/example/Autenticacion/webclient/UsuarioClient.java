package com.example.Autenticacion.webclient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
public class UsuarioClient {

    private final WebClient webClient;

    public UsuarioClient(@Value("${usuario-service.url}") String baseUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public Map<String, Object> obtenerUsuarioPorCorreo(String correo) {
        return webClient.get()
                .uri("/correo/{correo}", correo)
                .retrieve()
                .onStatus(status -> status.is4xxClientError(), response -> Mono.empty())
                .bodyToMono(Map.class)
                .block();
    }
}
