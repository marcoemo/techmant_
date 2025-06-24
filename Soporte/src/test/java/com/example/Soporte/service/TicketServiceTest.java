package com.example.Soporte.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;
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
    void obtenerTickets_retornaListaVacia() {
        when(ticketRepository.findAll()).thenReturn(Collections.emptyList());

        List<Ticket> resultado = ticketService.obtenerTickets();

        assertThat(resultado).isEmpty();
    }

    @Test
    void obtenerTicketPorId_retornaTicket() {
        Ticket ticket = new Ticket(2L, "Duda", 2L, "Descripción ejemplo");
        when(ticketRepository.findById(2L)).thenReturn(Optional.of(ticket));

        Optional<Ticket> resultado = ticketService.obtenerTicketPorId(2L);

        assertThat(resultado).isPresent();
        assertThat(resultado.get()).isEqualTo(ticket);
    }

    @Test
    void obtenerTicketPorId_retornaVacioSiNoExiste() {
        when(ticketRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<Ticket> resultado = ticketService.obtenerTicketPorId(99L);

        assertThat(resultado).isEmpty();
    }

    @Test
    void obtenerTicketsPorUsuario_retornaLista() {
        Ticket ticket = new Ticket(3L, "Reclamo", 5L, "Sistema lento");
        when(ticketRepository.findByUsuarioId(5L)).thenReturn(List.of(ticket));

        List<Ticket> resultado = ticketService.obtenerTicketsPorUsuario(5L);

        assertThat(resultado).containsExactly(ticket);
    }

    @Test
    void crearTicket_retornaTicketCreado() {
        Ticket ticketACrear = new Ticket(null, "Sugerencia", 4L, "Nueva funcionalidad");
        Ticket ticketCreado = new Ticket(10L, "Sugerencia", 4L, "Nueva funcionalidad");

        when(ticketRepository.save(any(Ticket.class))).thenReturn(ticketCreado);

        Optional<Ticket> resultado = ticketService.crearTicket(ticketACrear, null);

        assertThat(resultado).isPresent();
        assertThat(resultado.get().getIdTicket()).isEqualTo(10L);
    }

    @Test
    void crearTicket_retornaVacioConTipoInvalido() {
        Ticket ticketInvalido = new Ticket(null, "Invalido", 4L, "Tipo incorrecto");

        Optional<Ticket> resultado = ticketService.crearTicket(ticketInvalido, null);

        assertThat(resultado).isEmpty();
        verify(ticketRepository, never()).save(any());
    }

    @Test
    void actualizarTicket_retornaTicketActualizado() {
        Ticket ticketExistente = new Ticket(7L, "Duda", 1L, "Antigua descripción");
        Ticket ticketNuevo = new Ticket(null, "Duda", 8L, "Descripción actualizada");
        Ticket ticketGuardado = new Ticket(7L, "Duda", 8L, "Descripción actualizada");

        when(ticketRepository.findById(7L)).thenReturn(Optional.of(ticketExistente));
        when(ticketRepository.save(any(Ticket.class))).thenReturn(ticketGuardado);

        Optional<Ticket> resultado = ticketService.actualizarTicket(7L, ticketNuevo);

        assertThat(resultado).isPresent();
        assertThat(resultado.get().getUsuarioId()).isEqualTo(8L);
        assertThat(resultado.get().getDescripcion()).isEqualTo("Descripción actualizada");
    }

   @Test
 void actualizarTicket_retornaVacioSiNoExiste() {
    when(ticketRepository.findById(99L)).thenReturn(Optional.empty());

    // No pasar campos nulos en dudaSug o tipoTicket
    Ticket ticketParaActualizar = new Ticket(null, "Duda", 8L, "Descripción válida");

    Optional<Ticket> resultado = ticketService.actualizarTicket(99L, ticketParaActualizar);

    assertThat(resultado).isEmpty();
    verify(ticketRepository, never()).save(any());
}

   @Test
    void eliminarTicket_verificaLlamadaDelete() {
    when(ticketRepository.existsById(11L)).thenReturn(true); // mockea existencia

    doNothing().when(ticketRepository).deleteById(11L);

    ticketService.eliminarTicket(11L);

    verify(ticketRepository, times(1)).deleteById(11L);
    }

}
