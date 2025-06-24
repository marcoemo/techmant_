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
            new Tecnico(1L,1L,"Disponible",1L),
            new Tecnico(2L, 1L, "Ocupado",2L)
        );
        Mockito.when(asignacionService.obtenerTodos()).thenReturn(tecnicos);

        mockMvc.perform(get("/asignacion"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(jsonPath("$[0].idAsignacion").value(1L))
            .andExpect(jsonPath("$[0].usuarioId").value(1L))
            .andExpect(jsonPath("$[0].disponibilidad").value("Disponible"))
            .andExpect(jsonPath("$[0].solicitudId").value(1L))
            .andExpect(jsonPath("$[1].idAsignacion").value(2L))
            .andExpect(jsonPath("$[1].usuarioId").value(1L))
            .andExpect(jsonPath("$[1].disponibilidad").value("Ocupado"))
            .andExpect(jsonPath("$[1].solicitudId").value(2L));
    }

    @Test
    void buscarPorId_devuelveTecnico_siExiste() throws Exception {
        Tecnico tecnico = new Tecnico(1L, 1L, "Disponible", 1L);
        Mockito.when(asignacionService.buscarPorId(1L)).thenReturn(tecnico);

        mockMvc.perform(get("/asignacion/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.idAsignacion").value(1L))
            .andExpect(jsonPath("$.usuarioId").value(1L))
            .andExpect(jsonPath("$.disponibilidad").value("Disponible"))
            .andExpect(jsonPath("$.solicitudId").value(1L));
    }

    @Test
    void buscarPorId_devuelveNotFound_siNoExiste() throws Exception {
        Mockito.when(asignacionService.buscarPorId(99L)).thenReturn(null);

        mockMvc.perform(get("/asignacion/99"))
            .andExpect(status().isNotFound());
    }

    @Test
    void eliminarPorId_llamaService() throws Exception {
        mockMvc.perform(delete("/asignacion/1"))
            .andExpect(status().isOk());
        verify(asignacionService).eliminarPorId(1L);
    }

    @Test
    void modificarDisponibilidad_actualizaYDevuelveOk() throws Exception {
    Mockito.when(asignacionService.modificarDisponibilidad(1L, "Ocupado")).thenReturn(true);

    mockMvc.perform(put("/asignacion/1/disponibilidad")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"nuevaDisponibilidad\":\"Ocupado\"}"))
        .andExpect(status().isOk())
        .andExpect(content().string("Tecnico modificado"));

    verify(asignacionService).modificarDisponibilidad(1L, "Ocupado");
    }

    @Test
    void agregarTecnico_devuelveTecnicoCreado() throws Exception {
        Tecnico tecnico = new Tecnico(3L, 2L, "Disponible", 5L);
        Mockito.when(asignacionService.agregarTecnico("Juan", "Disponible", 2L, 5L)).thenReturn(tecnico);

        String body = "{\"nombre\":\"Juan\",\"disponibilidad\":\"Disponible\",\"usuarioId\":\"2\",\"solicitudId\":\"5\"}";

        mockMvc.perform(post("/asignacion")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.idAsignacion").value(3L))
            .andExpect(jsonPath("$.usuarioId").value(2L))
            .andExpect(jsonPath("$.disponibilidad").value("Disponible"))
            .andExpect(jsonPath("$.solicitudId").value(5L));
    }
}
