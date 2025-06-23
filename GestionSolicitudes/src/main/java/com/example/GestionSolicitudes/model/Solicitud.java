package com.example.GestionSolicitudes.model;


import java.time.LocalDate;
import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "solicitudes")
@AllArgsConstructor
@NoArgsConstructor

@Schema(description = "Modelo de Solicitud de Servicio")
public class Solicitud {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único de la solicitud")
    private Long solicitudId;

    @Column(nullable = false, length = 500)
    @Schema(description = "Descripción del problema reportado en la solicitud")
    private String descripcionProblema;

    @Column(nullable = false)
    @Schema(description = "Fecha de creación de la solicitud")
    private LocalDate fechaCreacion;

    @Column(nullable = true)
    @Schema(description = "Fecha de cierre de la solicitud")
    private Date fechaCierre;

    @Column(nullable = false, length = 20)
    @Schema(description = "Estado actual de la solicitud")
    private String estado;

    @Schema(description = "Identificador del usuario que creó la solicitud")
    private Long usuarioId;
    
}