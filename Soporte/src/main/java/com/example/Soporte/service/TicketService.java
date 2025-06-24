package com.example.Soporte.service;

import java.util.List;
import java.util.Optional;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.Soporte.model.Ticket;
import com.example.Soporte.repository.TicketRepository;

@Service
@Transactional
public class TicketService implements CommandLineRunner {

    private final TicketRepository ticketRepository;

    // Tipos válidos de tickets
    private final List<String> tiposValidos = List.of("Duda", "Sugerencia", "Reclamo");

    // Simulación ID de soporte (rol 4) fijo para asignar
    private final Long soporteIdAsignado = 4L;

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    // Precarga algunos tickets iniciales al iniciar la app
    @Override
    public void run(String... args) throws Exception {
        if (ticketRepository.count() == 0) {
            ticketRepository.save(new Ticket(null, "Duda", 1L, soporteIdAsignado, "No puedo ingresar al sistema"));
            ticketRepository.save(new Ticket(null, "Sugerencia", 2L, soporteIdAsignado, "Agregar opción de reporte"));
            ticketRepository.save(new Ticket(null, "Reclamo", 3L, soporteIdAsignado, "El sistema es muy lento"));
            System.out.println("Tickets base precargados: Duda, Sugerencia, Reclamo.");
        }
    }

    // Listar todos los tickets
    public List<Ticket> obtenerTickets() {
        return ticketRepository.findAll();
    }

    // Obtener ticket por ID
    public Optional<Ticket> obtenerTicketPorId(Long id) {
        return ticketRepository.findById(id);
    }

    // Obtener tickets por usuario
    public List<Ticket> obtenerTicketsPorUsuario(Long usuarioId) {
        return ticketRepository.findByUsuarioId(usuarioId);
    }

    // Crear ticket validando tipo y asignando soporte automáticamente
    public Optional<Ticket> crearTicket(Ticket ticket) {
        if (!tiposValidos.contains(ticket.getTipoTicket())) {
            return Optional.empty();
        }
        ticket.setSoporteId(soporteIdAsignado); // asigna soporte fijo
        return Optional.of(ticketRepository.save(ticket));
    }

    // Actualizar ticket si existe y tipo válido
    public Optional<Ticket> actualizarTicket(Long id, Ticket ticketActualizado) {
        if (!tiposValidos.contains(ticketActualizado.getTipoTicket())) {
            return Optional.empty();
        }
        return ticketRepository.findById(id).map(ticketExistente -> {
            ticketExistente.setTipoTicket(ticketActualizado.getTipoTicket());
            ticketExistente.setUsuarioId(ticketActualizado.getUsuarioId());
            ticketExistente.setDescripcion(ticketActualizado.getDescripcion());
            // No se permite cambiar soporteId desde aquí
            return ticketRepository.save(ticketExistente);
        });
    }

    // Eliminar ticket por id
    public boolean eliminarTicket(Long id) {
        if (ticketRepository.existsById(id)) {
            ticketRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
