package com.example.Seguimiento.client;

import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.beans.factory.annotation.Value;
    
@Component
public class SeguimientoClient {

    private final WebClient webClient;

  public SeguimientoClient(@Value("${solicitud-service.url}") String baseUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }
   public Map<String, Object> obtenerSolicitudId(Long id) {
        return this.webClient.get()
                .uri("/{id}", id)
                .retrieve()
                .onStatus(status -> status.is4xxClientError(),
                    response -> response.bodyToMono(String.class)
                    .map(body -> new RuntimeException("Solicitud no encontrada")))
                .bodyToMono(Map.class)
                .block();   
    }
}