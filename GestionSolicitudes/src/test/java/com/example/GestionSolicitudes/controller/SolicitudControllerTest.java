package com.example.GestionSolicitudes.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.example.GestionSolicitudes.model.Solicitud;
import com.example.GestionSolicitudes.service.SolicitudService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(SolicitudController.class)
public class SolicitudControllerTest {

    @MockBean
    private SolicitudService Service;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getAllSolicitud_returnsOKAndJson() throws Exception {
        Solicitud solicitud = new Solicitud();
        solicitud.setSolicitudId(1L);
        solicitud.setDescripcionProblema("Prueba");
        solicitud.setFechaCreacion(LocalDate.now());
        solicitud.setEstado("Abierta");
        solicitud.setUsuarioId(1L);

        List<Solicitud> solicitudes = Arrays.asList(solicitud);

        when(Service.obtenerTodas()).thenReturn(solicitudes);

        mockMvc.perform(get("/solicitudes"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].solicitudId").value(1L));
    }

    @Test
    void getSolicitudById_found() throws Exception {
        Solicitud solicitud = new Solicitud();
        solicitud.setSolicitudId(2L);
        solicitud.setDescripcionProblema("Test");
        solicitud.setFechaCreacion(LocalDate.now());
        solicitud.setEstado("Cerrada");
        solicitud.setUsuarioId(2L);

        when(Service.obtenerPorId(2L)).thenReturn(Optional.of(solicitud));

        mockMvc.perform(get("/solicitudes/2"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.solicitudId").value(2L))
            .andExpect(jsonPath("$.descripcionProblema").value("Test"));
    }

    @Test
    void getSolicitudById_notFound() throws Exception {
        when(Service.obtenerPorId(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/solicitudes/99"))
            .andExpect(status().isNotFound());
    }

    @Test
    void addSolicitud_returnsOkAndJson() throws Exception {
        Solicitud solicitud = new Solicitud();
        solicitud.setDescripcionProblema("Nuevo problema");
        solicitud.setFechaCreacion(LocalDate.of(2024, 1, 1));
        solicitud.setFechaCierre(null);
        solicitud.setEstado("Abierta");
        solicitud.setUsuarioId(3L);

        Solicitud saved = new Solicitud();
        saved.setSolicitudId(10L);
        saved.setDescripcionProblema("Nuevo problema");
        saved.setFechaCreacion(LocalDate.of(2024, 1, 1));
        saved.setFechaCierre(null);
        saved.setEstado("Abierta");
        saved.setUsuarioId(3L);

        when(Service.agregarSolicitud(
                solicitud.getDescripcionProblema(),
                solicitud.getFechaCreacion(),
                solicitud.getFechaCierre(),
                solicitud.getEstado(),
                solicitud.getUsuarioId()
        )).thenReturn(saved);

        String json = """
            {
                "descripcionProblema": "Nuevo problema",
                "fechaCreacion": "2024-01-01",
                "fechaCierre": null,
                "estado": "Abierta",
                "reporteId": 1,
                "usuarioId": 3
            }
            """;

        mockMvc.perform(post("/solicitudes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.solicitudId").value(10L))
            .andExpect(jsonPath("$.descripcionProblema").value("Nuevo problema"));
    }

    @Test
    void updateSolicitud_found() throws Exception {
    Solicitud solicitud = new Solicitud();
    solicitud.setSolicitudId(5L);
    solicitud.setDescripcionProblema("Actualizar");
    solicitud.setFechaCreacion(LocalDate.of(2024, 2, 2));
    // Conversión de LocalDate a Date
    LocalDate fechaCierreLocal = LocalDate.of(2024, 2, 5);
    Date fechaCierre = Date.from(fechaCierreLocal.atStartOfDay(ZoneId.systemDefault()).toInstant());
    solicitud.setFechaCierre(fechaCierre);
    solicitud.setEstado("Cerrada");
    solicitud.setUsuarioId(4L);


when(Service.actualizar(eq(5L), any(Solicitud.class))).thenReturn(Optional.of(solicitud));

    String json = """
        {
            "solicitudId": 5,
            "descripcionProblema": "Actualizar",
            "fechaCreacion": "2024-02-02",
            "fechaCierre": "2024-02-05",
            "estado": "Cerrada",
            "reporteId": 2,
            "usuarioId": 4
        }
        """;

    mockMvc.perform(put("/solicitudes/5")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.solicitudId").value(5L))
        .andExpect(jsonPath("$.estado").value("Cerrada"));
}

    @Test
    void updateSolicitud_notFound() throws Exception {
        Solicitud solicitud = new Solicitud();
        solicitud.setSolicitudId(99L);

        when(Service.actualizar(99L, solicitud)).thenReturn(Optional.empty());

        String json = """
            {
                "solicitudId": 99
            }
            """;

        mockMvc.perform(put("/solicitudes/99")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andExpect(status().isNotFound());
    }

    @Test
    void deleteSolicitud_found() throws Exception {
        when(Service.eliminar(7L)).thenReturn(true);

        mockMvc.perform(delete("/solicitudes/7"))
            .andExpect(status().isNoContent());
    }

    @Test
    void deleteSolicitud_notFound() throws Exception {
        when(Service.eliminar(77L)).thenReturn(false);

        mockMvc.perform(delete("/solicitudes/77"))
            .andExpect(status().isNotFound());
    }

    @Test
    void getSolicitudesByUsuario_returnsOKAndJson() throws Exception {
        Solicitud solicitud = new Solicitud();
        solicitud.setSolicitudId(8L);
        solicitud.setUsuarioId(100L);

        when(Service.filtrarPorUsuario(100L)).thenReturn(List.of(solicitud));

        mockMvc.perform(get("/solicitudes/usuario/100"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].solicitudId").value(8L));
    }
}
