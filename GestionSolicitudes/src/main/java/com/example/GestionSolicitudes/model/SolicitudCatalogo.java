package com.example.GestionSolicitudes.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "solicitudes_catalogos")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class SolicitudCatalogo {

    @EmbeddedId
    private SolicitudCatalogoId id;

    @ManyToOne
    @MapsId("solicitudId")
    @JoinColumn(name = "solicitud_id")
    private Solicitud solicitud;//fk solicitud

    @Column(nullable = false)
    private int subtotal; //atributo de la tabla intermedia

    
}

// {
//   "id": {
//     "solicitudId": 1,
//     "idCatalogo": 3
//   },
//   "subtotal": 15000
// }

//GET /solicitudes-catalogos/{solicitudId}/{idCatalogo} para obtener 