package com.example.Diagnosticos.controller;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.Diagnosticos.model.Diagnostico;
import com.example.Diagnosticos.service.DiagnosticoService;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;



@WebMvcTest(DiagnosticoController.class)
public class DiagnosticoControllerTest {

    @MockBean
    private DiagnosticoService diagnosticoService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getAllDiagnosticos_returnsOKAndJson() throws Exception {
        List<Diagnostico> diagnosticos = Arrays.asList(
            new Diagnostico(1L, "Detalle", "estado", LocalDateTime.now(), 1L)
        );
        when(diagnosticoService.obtenerTodos()).thenReturn(diagnosticos);

        mockMvc.perform(get("/diagnosticos"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].diagnosticoId").value(1L));
    }

    @Test
    void getDiagnosticoById_found_returnsOKAndJson() throws Exception {
        Diagnostico diagnostico = new Diagnostico(2L, "Detalle2", "estado2", LocalDateTime.now(), 2L);
        when(diagnosticoService.obtenerPorId(2L)).thenReturn(diagnostico);

        mockMvc.perform(get("/diagnosticos/2"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.diagnosticoId").value(2L))
            .andExpect(jsonPath("$.detalle").value("Detalle2"));
    }

    @Test
    void getDiagnosticoById_notFound_returns404() throws Exception {
        when(diagnosticoService.obtenerPorId(99L)).thenReturn(null);

        mockMvc.perform(get("/diagnosticos/99"))
            .andExpect(status().isNotFound());
    }

    @Test
    void deleteDiagnostico_returnsNoContent() throws Exception {
        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete("/diagnosticos/1"))
            .andExpect(status().isNoContent());
    }

    @Test
    void updateDiagnostico_found_returnsOKAndJson() throws Exception {
        Diagnostico diagnostico = new Diagnostico(3L, "Detalle3", "estado3", LocalDateTime.now(), 3L);
        when(diagnosticoService.actualizar(org.mockito.ArgumentMatchers.eq(3L), org.mockito.ArgumentMatchers.any(Diagnostico.class)))
            .thenReturn(diagnostico);

        String diagnosticoJson = """
            {
                "diagnosticoId":3,
                "detalle":"Detalle3",
                "estado":"estado3",
                "fecha":"2023-01-01T10:00:00",
                "idAsignacion":3
            }
            """;

        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put("/diagnosticos/3")
                .contentType("application/json")
                .content(diagnosticoJson))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.diagnosticoId").value(3L))
            .andExpect(jsonPath("$.detalle").value("Detalle3"));
    }

    @Test
    void updateDiagnostico_notFound_returns404() throws Exception {
        when(diagnosticoService.actualizar(org.mockito.ArgumentMatchers.eq(100L), org.mockito.ArgumentMatchers.any(Diagnostico.class)))
            .thenReturn(null);

        String diagnosticoJson = """
            {
                "diagnosticoId":100,
                "detalle":"No existe",
                "estado":"estado",
                "fecha":"2023-01-01T10:00:00",
                "idAsignacion":10
            }
            """;

        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put("/diagnosticos/100")
                .contentType("application/json")
                .content(diagnosticoJson))
            .andExpect(status().isNotFound());
    }
}