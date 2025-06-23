package com.example.Asignaciones.controller;

import com.example.Asignaciones.model.Tecnico;
import com.example.Asignaciones.service.AsignacionService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AsignacionController.class)
@AutoConfigureMockMvc(addFilters = false)
public class AsignacionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AsignacionService asignacionService;

    @Test
    void obtenerTodos_devuelveListaTecnicos() throws Exception {
        List<Tecnico> tecnicos = List.of(
            new Tecnico(1L, "Ricardo", "Disponible"),
            new Tecnico(2L, "Dariel", "Ocupado")
        );
        Mockito.when(asignacionService.obtenerTodos()).thenReturn(tecnicos);

        mockMvc.perform(get("/asignacion"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(jsonPath("$[0].idAsignacion").value(1L))
            .andExpect(jsonPath("$[0].nombreTecnico").value("Ricardo"))
            .andExpect(jsonPath("$[0].disponibilidad").value("Disponible"))
            .andExpect(jsonPath("$[1].idAsignacion").value(2L))
            .andExpect(jsonPath("$[1].nombreTecnico").value("Dariel"))
            .andExpect(jsonPath("$[1].disponibilidad").value("Ocupado"));
    }

    @Test
    void buscarPorId_tecnicoExistente_devuelveTecnico() throws Exception {
        Tecnico tecnico = new Tecnico(1L, "Ricardo", "Disponible");
        Mockito.when(asignacionService.buscarPorId(1L)).thenReturn(tecnico);

        mockMvc.perform(get("/asignacion/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.idAsignacion").value(1L))
            .andExpect(jsonPath("$.nombreTecnico").value("Ricardo"))
            .andExpect(jsonPath("$.disponibilidad").value("Disponible"));
    }

    @Test
    void buscarPorId_tecnicoNoExiste_devuelve404() throws Exception {
        Mockito.when(asignacionService.buscarPorId(999L)).thenReturn(null);

        mockMvc.perform(get("/asignacion/999"))
            .andExpect(status().isNotFound());
    }

    @Test
    void eliminarPorId_llamaServicio() throws Exception {
        mockMvc.perform(delete("/asignacion/1"))
            .andExpect(status().isOk());

        verify(asignacionService).eliminarPorId(1L);
    }

    @Test
void modificarDisponibilidad_devuelveOk() throws Exception {
    Mockito.doNothing().when(asignacionService).modificarDisponibilidad(Mockito.eq(1L), Mockito.eq("Disponible"));

    mockMvc.perform(put("/asignacion/1/disponibilidad")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"nuevaDisponibilidad\": \"Disponible\"}"))
        .andExpect(status().isOk())
        .andExpect(content().string("Tecnico modificado"));

    verify(asignacionService).modificarDisponibilidad(1L, "Disponible");
}

    @Test
    void agregarTecnico_devuelveTecnicoCreado() throws Exception {
        Tecnico nuevo = new Tecnico(4L, "Pablo", "Ocupado");
        Mockito.when(asignacionService.agregarTecnico(eq("Pablo"), eq("Ocupado"))).thenReturn(nuevo);

        mockMvc.perform(post("/asignacion")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                      "nombre": "Pablo",
                      "disponibilidad": "Ocupado"
                    }
                    """))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.idAsignacion").value(4L))
            .andExpect(jsonPath("$.nombreTecnico").value("Pablo"))
            .andExpect(jsonPath("$.disponibilidad").value("Ocupado"));
    }
}
