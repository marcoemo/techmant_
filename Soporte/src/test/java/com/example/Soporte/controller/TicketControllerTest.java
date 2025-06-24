package com.example.Soporte.controller;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.example.Soporte.model.Ticket;
import com.example.Soporte.service.TicketService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(TicketController.class)
public class TicketControllerTest {

    @MockBean
    private TicketService ticketService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void listarTickets_retornaNoContentCuandoVacio() throws Exception {
        when(ticketService.obtenerTickets()).thenReturn(Arrays.asList());

        mockMvc.perform(get("/tickets"))
            .andExpect(status().isNoContent());
    }

    @Test
    void obtenerTicketPorId_retornaOkYTicket() throws Exception {
        Ticket ticket = new Ticket(2L, "Duda", 10L, "No funciona la app");
        when(ticketService.obtenerTicketPorId(2L)).thenReturn(Optional.of(ticket));

        mockMvc.perform(get("/tickets/2"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.idTicket").value(2L))
            .andExpect(jsonPath("$.tipoTicket").value("Duda"))
            .andExpect(jsonPath("$.usuarioId").value(10L))
            .andExpect(jsonPath("$.descripcion").value("No funciona la app"));
    }

    @Test
    void obtenerTicketPorId_retornaNotFoundCuandoNoExiste() throws Exception {
        when(ticketService.obtenerTicketPorId(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/tickets/99"))
            .andExpect(status().isNotFound());
    }

    @Test
    void listarTicketsPorUsuario_retornaOkConLista() throws Exception {
        List<Ticket> tickets = Arrays.asList(
            new Ticket(3L, "Reclamo", 5L, "Lento el sistema")
        );
        when(ticketService.obtenerTicketsPorUsuario(5L)).thenReturn(tickets);

        mockMvc.perform(get("/tickets/usuario/5"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].idTicket").value(3L))
            .andExpect(jsonPath("$[0].tipoTicket").value("Reclamo"))
            .andExpect(jsonPath("$[0].usuarioId").value(5L));
    }

    @Test
    void listarTicketsPorUsuario_retornaNoContentCuandoListaVacia() throws Exception {
        when(ticketService.obtenerTicketsPorUsuario(999L)).thenReturn(Arrays.asList());

        mockMvc.perform(get("/tickets/usuario/999"))
            .andExpect(status().isNoContent());
    }

    @Test
    void crearTicket_retornaCreated() throws Exception {
        Ticket entrada = new Ticket(null, "Sugerencia", 7L, "Agregar tema oscuro");
        Ticket creado = new Ticket(10L, "Sugerencia", 7L, "Agregar tema oscuro");

        when(ticketService.crearTicket(any(Ticket.class), isNull())).thenReturn(Optional.of(creado));

        String json = """
            {
              "tipoTicket": "Sugerencia",
              "usuarioId": 7,
              "descripcion": "Agregar tema oscuro"
            }
            """;

        mockMvc.perform(post("/tickets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.idTicket").value(10L))
            .andExpect(jsonPath("$.tipoTicket").value("Sugerencia"))
            .andExpect(jsonPath("$.usuarioId").value(7))
            .andExpect(jsonPath("$.descripcion").value("Agregar tema oscuro"));
    }

    @Test
    void crearTicket_retornaBadRequestPorTipoInvalido() throws Exception {
        when(ticketService.crearTicket(any(Ticket.class), isNull())).thenReturn(Optional.empty());

        String json = """
            {
              "tipoTicket": "Otro",
              "usuarioId": 7,
              "descripcion": "Descripción inválida"
            }
            """;

        mockMvc.perform(post("/tickets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andExpect(status().isBadRequest());
    }

    @Test
    void actualizarTicket_retornaOk() throws Exception {
        Ticket actualizado = new Ticket(7L, "Reclamo", 8L, "Actualizado");

        when(ticketService.actualizarTicket(eq(7L), any(Ticket.class))).thenReturn(Optional.of(actualizado));

        String json = """
            {
              "tipoTicket": "Reclamo",
              "usuarioId": 8,
              "descripcion": "Actualizado"
            }
            """;

        mockMvc.perform(put("/tickets/7")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.idTicket").value(7L))
            .andExpect(jsonPath("$.tipoTicket").value("Reclamo"))
            .andExpect(jsonPath("$.usuarioId").value(8))
            .andExpect(jsonPath("$.descripcion").value("Actualizado"));
    }

    @Test
    void actualizarTicket_retornaNotFoundCuandoNoExiste() throws Exception {
        when(ticketService.actualizarTicket(eq(99L), any(Ticket.class))).thenReturn(Optional.empty());

        String json = """
            {
              "tipoTicket": "Duda",
              "usuarioId": 1,
              "descripcion": "No existe"
            }
            """;

        mockMvc.perform(put("/tickets/99")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andExpect(status().isNotFound());
    }

    @Test
    void eliminarTicket_retornaNoContent() throws Exception {
        when(ticketService.eliminarTicket(11L)).thenReturn(true);

        mockMvc.perform(delete("/tickets/11"))
            .andExpect(status().isNoContent());
    }

    @Test
    void eliminarTicket_retornaNotFoundCuandoNoExiste() throws Exception {
        when(ticketService.eliminarTicket(99L)).thenReturn(false);

        mockMvc.perform(delete("/tickets/99"))
            .andExpect(status().isNotFound());
    }
}
