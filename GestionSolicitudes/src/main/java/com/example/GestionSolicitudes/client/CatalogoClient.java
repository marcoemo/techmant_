package com.example.GestionSolicitudes.client;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

// esta clase para acceder al servicio-catalogo m:m
@Component
public class CatalogoClient {

    private final WebClient webClient;

    public CatalogoClient(@Value("${catalogo-service.url}") String catalogoUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(catalogoUrl)
                .build();
    }

    public Map<String, Object> obtenerCatalogoPorId(Long id) {
        return this.webClient.get()
                .uri("/catalogo/{id}", id)
                .retrieve()
                .bodyToMono(Map.class)
                .block();
    }
}
