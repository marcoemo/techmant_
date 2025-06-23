package com.example.Seguimiento.controller;

import com.example.Seguimiento.model.Seguimiento;
import com.example.Seguimiento.service.SeguimientoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SeguimientoController.class)
public class SeguimientoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SeguimientoService service;

    private final Seguimiento ejemplo = new Seguimiento(1L, "En diagnóstico", "Falla detectada", 3L);

    @Test
    void obtenerTodos_debeRetornarLista() throws Exception {
        when(service.obtenerTodos()).thenReturn(List.of(ejemplo));

        mockMvc.perform(get("/seguimientos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].estado").value("En diagnóstico"));
    }

    @Test
    void obtenerPorId_existente_debeRetornarSeguimiento() throws Exception {
        when(service.obtenerPorId(1L)).thenReturn(Optional.of(ejemplo));

        mockMvc.perform(get("/seguimientos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.estado").value("En diagnóstico"));
    }

    @Test
    void obtenerPorId_inexistente_retorna404() throws Exception {
        when(service.obtenerPorId(999L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/seguimientos/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void crearSeguimiento_retorna201() throws Exception {
        when(service.crear(any(Seguimiento.class))).thenReturn(ejemplo);

        mockMvc.perform(post("/seguimientos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                                "estado": "En diagnóstico",
                                "observaciones": "Falla detectada",
                                "solicitudId": 3
                            }
                        """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.estado").value("En diagnóstico"));
    }

    @Test
    void actualizarSeguimiento_existente_retorna200() throws Exception {
        when(service.actualizar(any(Long.class), any(Seguimiento.class)))
                .thenReturn(Optional.of(ejemplo));

        mockMvc.perform(put("/seguimientos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                                "estado": "En diagnóstico",
                                "observaciones": "Falla detectada",
                                "solicitudId": 3
                            }
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.estado").value("En diagnóstico"));
    }

    @Test
    void actualizarSeguimiento_inexistente_retorna404() throws Exception {
        when(service.actualizar(any(Long.class), any(Seguimiento.class))).thenReturn(Optional.empty());

        mockMvc.perform(put("/seguimientos/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                                "estado": "Reparado",
                                "observaciones": "Placa reemplazada",
                                "solicitudId": 1
                            }
                        """))
                .andExpect(status().isNotFound());
    }

    @Test
    void eliminarSeguimiento_retorna204() throws Exception {
        mockMvc.perform(delete("/seguimientos/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void filtrarPorSolicitud_retornaLista() throws Exception {
        when(service.filtrarPorSolicitud(3L)).thenReturn(List.of(ejemplo));

        mockMvc.perform(get("/seguimientos/solicitud/3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].estado").value("En diagnóstico"));
    }
}
