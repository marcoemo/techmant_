package com.example.Soporte.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Collections;

import com.example.Soporte.model.Ticket;
import com.example.Soporte.repository.TicketRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TicketServiceTest {

    @Mock
    private TicketRepository ticketRepository;

    @InjectMocks
    private TicketService ticketService;

    @Test
    void getAllTickets_returnsEmptyList() {
        when(ticketRepository.findAll()).thenReturn(Collections.emptyList());

        List<Ticket> result = ticketService.getTicket();

        assertThat(result).isEmpty();
    }

    @Test
    void getTicketById_returnsTicket() {
        Ticket ticket = new Ticket(2L, "Ticket 2", 2L);
        when(ticketRepository.findById(2L)).thenReturn(java.util.Optional.of(ticket));

        Ticket result = ticketService.getTicket(2L);

        assertThat(result).isEqualTo(ticket);
    }

    @Test
    void getTicketById_throwsExceptionWhenNotFound() {
        when(ticketRepository.findById(99L)).thenReturn(java.util.Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            ticketService.getTicket(99L);
        });
    }

    @Test
    void getTicketsPorUsuario_returnsList() {
        Ticket ticket = new Ticket(3L, "Ticket 3", 5L);
        when(ticketRepository.findByUsuarioId(5L)).thenReturn(List.of(ticket));

        List<Ticket> result = ticketService.getTicketsPorUsuario(5L);

        assertThat(result).containsExactly(ticket);
    }

    @Test
    void saveTicket_returnsSavedTicket() {
        Ticket ticketToSave = new Ticket(null, "Nuevo Ticket", 4L);
        Ticket savedTicket = new Ticket(10L, "Nuevo Ticket", 4L);
        when(ticketRepository.save(ticketToSave)).thenReturn(savedTicket);

        Ticket result = ticketService.saveTicket(ticketToSave);

        assertThat(result).isEqualTo(savedTicket);
    }

    @Test
    void actualizarTicket_returnsUpdatedTicket() {
        Ticket ticketExistente = new Ticket(7L, "Duda o sugerencia", 1L);
        Ticket ticketNuevo = new Ticket(null, "Duda o sugerencia", 8L);
        Ticket ticketFinal = new Ticket(7L, "Duda o sugerencia", 8L);

        when(ticketRepository.findById(7L)).thenReturn(java.util.Optional.of(ticketExistente));
        when(ticketRepository.save(any(Ticket.class))).thenReturn(ticketFinal);

        Ticket result = ticketService.actualizarTicket(7L, ticketNuevo);

        assertThat(result.getIdTicket()).isEqualTo(7L);
        assertThat(result.getDudaSug()).isEqualTo("Duda o sugerencia");
        assertThat(result.getUsuarioId()).isEqualTo(8L);
    }

    @Test
    void eliminarTicket_removesTicket() {
        doNothing().when(ticketRepository).deleteById(11L);

        ticketService.eliminarTicket(11L);

        verify(ticketRepository, times(1)).deleteById(11L);
    }
}
