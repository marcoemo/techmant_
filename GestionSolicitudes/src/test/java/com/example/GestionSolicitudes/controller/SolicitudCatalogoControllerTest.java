package com.example.GestionSolicitudes.controller;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.eq;

import java.util.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.GestionSolicitudes.model.SolicitudCatalogo;
import com.example.GestionSolicitudes.model.SolicitudCatalogoId;
import com.example.GestionSolicitudes.service.SolicitudCatalogoService;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(SolicitudCatalogoController.class)
public class SolicitudCatalogoControllerTest {

    @MockBean
    private SolicitudCatalogoService service;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getAllSolicitudCatalogo_returnsOKAndJson() throws Exception {
        SolicitudCatalogo sc = new SolicitudCatalogo();
        sc.setId(new SolicitudCatalogoId(1L, 2L));
        List<SolicitudCatalogo> solicitudCatalogo = Arrays.asList(sc);

        when(service.obtenerTodos()).thenReturn(solicitudCatalogo);

        mockMvc.perform(get("/solicitudes-catalogos"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id.solicitudId").value(1L))
            .andExpect(jsonPath("$[0].id.idCatalogo").value(2L));
    }

    @Test
    void getSolicitudCatalogoById_returnsOK_whenFound() throws Exception {
        SolicitudCatalogoId id = new SolicitudCatalogoId(1L, 2L);
        SolicitudCatalogo sc = new SolicitudCatalogo();
        sc.setId(id);

        when(service.obtenerPorId(eq(id))).thenReturn(Optional.of(sc));

        mockMvc.perform(get("/solicitudes-catalogos/1/2"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id.solicitudId").value(1L))
            .andExpect(jsonPath("$.id.idCatalogo").value(2L));
    }

    @Test
    void getSolicitudCatalogoById_returnsNotFound_whenNotFound() throws Exception {
        SolicitudCatalogoId id = new SolicitudCatalogoId(99L, 88L);
        when(service.obtenerPorId(eq(id))).thenReturn(Optional.empty());

        mockMvc.perform(get("/solicitudes-catalogos/99/88"))
            .andExpect(status().isNotFound());
    }

    @Test
    void agregarSolicitudCatalogo_returnsOK_whenValid() throws Exception {
        SolicitudCatalogo sc = new SolicitudCatalogo();
        sc.setId(new SolicitudCatalogoId(1L, 2L));
        sc.setSubtotal(10);

        when(service.agregarRelacion(1L, 2L, 10)).thenReturn(sc);

        String json = "{\"solicitudId\":\"1\",\"catalogoId\":\"2\",\"subtotal\":\"10\"}";

        mockMvc.perform(post("/solicitudes-catalogos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id.solicitudId").value(1L))
            .andExpect(jsonPath("$.id.idCatalogo").value(2L))
            .andExpect(jsonPath("$.subtotal").value(10));
    }

    @Test
    void agregarSolicitudCatalogo_returnsBadRequest_whenServiceReturnsNull() throws Exception {
        when(service.agregarRelacion(1L, 2L, 10)).thenReturn(null);

        String json = "{\"solicitudId\":\"1\",\"catalogoId\":\"2\",\"subtotal\":\"10\"}";

        mockMvc.perform(post("/solicitudes-catalogos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andExpect(status().isBadRequest());
    }
}