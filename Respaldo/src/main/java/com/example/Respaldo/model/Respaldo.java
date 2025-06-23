package com.example.Respaldo.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "respaldos")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Modelo de Respaldo de Base de Datos")
public class Respaldo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único del respaldo")
    private Long id;

    @Column(name = "nombre_archivo", nullable = false)
    @Schema(description = "Nombre del archivo de respaldo")
    private String nombreArchivo;

    @Column(name = "fecha_respaldo", nullable = false)
    @Schema(description = "Fecha y hora en que se realizó el respaldo")
    private LocalDateTime fechaRespaldo;

    @Column(name = "exito", nullable = false)
    @Schema(description = "Indica si el respaldo se realizó con éxito")
    private boolean exito;

    @Column(name = "mensaje")
    @Schema(description = "Mensaje adicional sobre el estado del respaldo")
    private String mensaje;
}