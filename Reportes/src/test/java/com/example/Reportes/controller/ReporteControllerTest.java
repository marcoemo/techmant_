package com.example.Reportes.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.Reportes.service.ReporteService;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(ReporteController.class)
public class ReporteControllerTest {

    @MockBean
    private ReporteService diagnosticoService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void deleteDiagnostico_returnsNoContent() throws Exception {
        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete("/reportes/1"))
            .andExpect(status().isNoContent());
    }

    @Test
    void agregarReporte_returnsOk() throws Exception {
        com.example.Reportes.model.Reporte reporte = new com.example.Reportes.model.Reporte();
        reporte.setFechaGeneracion(java.time.LocalDate.now());
        reporte.setCostoManoObra(100);
        reporte.setDiagnosticoId(1L);

        com.example.Reportes.model.Reporte nuevoReporte = new com.example.Reportes.model.Reporte();
        org.mockito.Mockito.when(diagnosticoService.agregarReporte(
                org.mockito.Mockito.any(),
                org.mockito.Mockito.anyInt(),
                org.mockito.Mockito.anyLong()))
            .thenReturn(nuevoReporte);

        String json = """
            {
                "fechaGeneracion": "2024-01-01",
                "costoManoObra": 100,
                "diagnosticoId": 1
            }
            """;

        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post("/reportes")
                .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                .content(json))
            .andExpect(status().isOk());
    }

    @Test
    void listarReportes_returnsOk() throws Exception {
        org.mockito.Mockito.when(diagnosticoService.obtenerTodos())
            .thenReturn(java.util.Collections.emptyList());

        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get("/reportes"))
            .andExpect(status().isOk());
    }

    @Test
    void obtenerReportePorId_found_returnsOk() throws Exception {
        com.example.Reportes.model.Reporte reporte = new com.example.Reportes.model.Reporte();
        org.mockito.Mockito.when(diagnosticoService.obtenerPorId(1L)).thenReturn(reporte);

        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get("/reportes/1"))
            .andExpect(status().isOk());
    }

    @Test
    void obtenerReportePorId_notFound_returnsNotFound() throws Exception {
        org.mockito.Mockito.when(diagnosticoService.obtenerPorId(99L)).thenReturn(null);

        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get("/reportes/99"))
            .andExpect(status().isNotFound());
    }
}
