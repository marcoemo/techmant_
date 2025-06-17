package com.example.Diagnosticos.Client;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class EquipoClient {
    
    private final WebClient webClient;

    public EquipoClient(@Value("${asignacion-service.url}") String equipoUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(equipoUrl)
                .build();
    }

    public Map<String, Object> obtenerEquipoPorId(Long id) {
        return this.webClient.get()
                .uri("/equipos/{id}", id)
                .retrieve()
                .bodyToMono(Map.class)
                .block();
    }
}
