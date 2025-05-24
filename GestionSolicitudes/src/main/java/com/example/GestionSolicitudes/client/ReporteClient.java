package com.example.GestionSolicitudes.client;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.reactive.function.client.WebClient;

public class ReporteClient {

    private final WebClient webClient;

    public ReporteClient(@Value("${reporte.url}") String reporteUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(reporteUrl)
                .build();
    }

    public Map<String, Object> obtenerReportePorId(Long id) {
        return this.webClient.get()
                .uri("/reporte/{id}", id)
                .retrieve()
                .bodyToMono(Map.class)
                .block();
    }
}
