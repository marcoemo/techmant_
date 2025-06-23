package com.example.GestionSolicitudes.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "solicitudes_catalogos")
@Data
@AllArgsConstructor
@NoArgsConstructor

@Schema(description = "Modelo de Solicitud de Catálogo de Servicios")
public class SolicitudCatalogo {

    @EmbeddedId
    private SolicitudCatalogoId id;

    @ManyToOne
    @MapsId("solicitudId")
    @JoinColumn(name = "solicitud_id")
    @Schema(description = "Solicitud asociada a este catálogo")
    private Solicitud solicitud;//fk solicitud

    @Column(nullable = false)
    @Schema(description = "Identificador del catálogo de servicios asociado")
    private int subtotal; //atributo de la tabla intermedia

    
}