package com.example.Soporte.controller;

import java.util.List;
import java.util.Optional;

import com.example.Soporte.model.Ticket;
import com.example.Soporte.service.TicketService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tickets")
@Tag(name = "Tickets", description = "Operaciones relacionadas con tickets de soporte")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @Operation(summary = "Listar todos los tickets")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de tickets obtenida correctamente"),
        @ApiResponse(responseCode = "204", description = "No hay tickets registrados")
    })
    @GetMapping
    public ResponseEntity<List<Ticket>> listarTickets() {
        List<Ticket> tickets = ticketService.obtenerTickets();
        if (tickets.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(tickets);
    }

    @Operation(summary = "Obtener ticket por su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Ticket encontrado"),
        @ApiResponse(responseCode = "404", description = "Ticket no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Ticket> obtenerTicketPorId(
        @Parameter(description = "ID del ticket a buscar") @PathVariable Long id) {
        Optional<Ticket> ticket = ticketService.obtenerTicketPorId(id);
        return ticket.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Listar tickets por ID de usuario")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Tickets encontrados para el usuario"),
        @ApiResponse(responseCode = "204", description = "El usuario no tiene tickets")
    })
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Ticket>> listarTicketsPorUsuario(
        @Parameter(description = "ID del usuario") @PathVariable Long usuarioId) {
        List<Ticket> tickets = ticketService.obtenerTicketsPorUsuario(usuarioId);
        if (tickets.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(tickets);
    }

    @Operation(summary = "Crear un nuevo ticket")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Ticket creado correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos para crear el ticket")
    })
    @PostMapping
    public ResponseEntity<?> crearTicket(@RequestBody Ticket ticket) {
        if (ticket.getTipoTicket() == null || ticket.getUsuarioId() == null || ticket.getDescripcion() == null || ticket.getDescripcion().isBlank()) {
            return ResponseEntity.badRequest().body("Tipo, usuario y descripción son obligatorios");
        }

        Optional<Ticket> creado = ticketService.crearTicket(ticket, null);
        if (creado.isEmpty()) {
            return ResponseEntity.badRequest().body("Tipo de ticket inválido. Debe ser: Duda, Sugerencia o Reclamo");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(creado.get());
    }

    @Operation(summary = "Actualizar un ticket existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Ticket actualizado correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos para actualizar el ticket"),
        @ApiResponse(responseCode = "404", description = "Ticket no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarTicket(
        @Parameter(description = "ID del ticket a actualizar") @PathVariable Long id,
        @RequestBody Ticket ticketActualizado) {

        if (ticketActualizado.getTipoTicket() == null || ticketActualizado.getUsuarioId() == null || ticketActualizado.getDescripcion() == null || ticketActualizado.getDescripcion().isBlank()) {
            return ResponseEntity.badRequest().body("Tipo, usuario y descripción son obligatorios");
        }

        Optional<Ticket> actualizado = ticketService.actualizarTicket(id, ticketActualizado);
        if (actualizado.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(actualizado.get());
    }

    @Operation(summary = "Eliminar un ticket por su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Ticket eliminado correctamente"),
        @ApiResponse(responseCode = "404", description = "Ticket no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarTicket(
        @Parameter(description = "ID del ticket a eliminar") @PathVariable Long id) {

        boolean eliminado = ticketService.eliminarTicket(id);
        if (eliminado) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
