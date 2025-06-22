package com.example.Soporte.controller;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.Soporte.model.Ticket;
import com.example.Soporte.service.TicketService;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


//cargamos el controlador que vamos a probar
@WebMvcTest(TicketController.class)
public class TicketControllerTest {

    //inyectamos un mock de ReservaService para manipular nuestro contexto
    @MockBean
    private TicketService ticketService;

    //se inyecta un mock proporcionado por spring para simular la llamada a la api
    @Autowired
    private MockMvc mockMvc;

    @Test
    void getAllTicket_returnsNoContentWhenEmpty() throws Exception {
        when(ticketService.getTicket()).thenReturn(Arrays.asList());
        mockMvc.perform(get("/ticket"))
            .andExpect(status().isNoContent());
    }

    @Test
    void getTicketById_returnsTicketAndOk() throws Exception {
        Ticket ticket = new Ticket(2L, "Ticket 2", 2L);
        when(ticketService.getTicket(2L)).thenReturn(ticket);

        mockMvc.perform(get("/ticket/2"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.idTicket").value(2L))
            .andExpect(jsonPath("$.dudaSug").value("Ticket 2"));
    }

    @Test
    void getTicketById_returnsNotFoundWhenException() throws Exception {
        when(ticketService.getTicket(99L)).thenThrow(new RuntimeException("Not found"));

        mockMvc.perform(get("/ticket/99"))
            .andExpect(status().isNotFound());
    }

    @Test
    void ticketsPorUsuario_returnsList() throws Exception {
        List<Ticket> tickets = Arrays.asList(new Ticket(3L, "Ticket 3", 5L));
        when(ticketService.getTicketsPorUsuario(5L)).thenReturn(tickets);

        mockMvc.perform(get("/ticket/usuario/5"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].idTicket").value(3L));
    }

    @Test
    void guardarTicket_returnsCreated() throws Exception {
        Ticket ticket = new Ticket(null, "Nuevo Ticket", 4L);
        Ticket guardado = new Ticket(10L, "Nuevo Ticket", 4L);
        when(ticketService.saveTicket(org.mockito.ArgumentMatchers.any(Ticket.class))).thenReturn(guardado);

        mockMvc.perform(
                org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post("/ticket")
                .contentType("application/json")
                .content("{\"descripcion\":\"Nuevo Ticket\",\"usuarioId\":4}")
            )
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.idTicket").value(10L));
    }

    @Test
    void actualizarTicket_returnsOk() throws Exception {
        Ticket actualizado = new Ticket(7L, "Actualizado", 8L);
        when(ticketService.actualizarTicket(org.mockito.ArgumentMatchers.eq(7L), org.mockito.ArgumentMatchers.any(Ticket.class)))
            .thenReturn(actualizado);

        mockMvc.perform(
                org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put("/ticket/7")
                .contentType("application/json")
                .content("{\"descripcion\":\"Actualizado\",\"usuarioId\":8}")
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.idTicket").value(7L))
            .andExpect(jsonPath("$.dudaSug").value("Actualizado"));
    }

    @Test
    void eliminarTicket_returnsNoContent() throws Exception {
        org.mockito.Mockito.doNothing().when(ticketService).eliminarTicket(11L);

        mockMvc.perform(
                org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete("/ticket/11")
            )
            .andExpect(status().isNoContent());
    }
}