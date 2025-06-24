package com.example.Soporte.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Tickets")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Entidad que representa un ticket de soporte creado por un usuario.")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único del ticket", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long idTicket;

    @Column(name = "tipo_ticket", nullable = true)
    @Schema(description = "Tipo de ticket: 'Duda', 'Sugerencia' o 'Reclamo'", example = "Duda", required = true)
    private String tipoTicket;

    @Column(name = "usuario_id", nullable = false)
    @Schema(description = "ID del usuario que creó el ticket", example = "10", required = true)
    private Long usuarioId;

    @Column(name = "descripcion", nullable = false)
    @Schema(description = "Descripción o detalle del ticket", example = "No puedo ingresar al sistema", required = true)
    private String descripcion;
}
