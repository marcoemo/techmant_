package com.example.Reportes.model;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "reportes_tecnico")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Modelo de reportes de diagnostico")
public class Reporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único del reporte", example = "1")
    private Long reporteId;

    @Column(nullable = false)
    @Schema(description = "Fecha de generación del reporte", example = "2024-06-01")
    private LocalDate fechaGeneracion;

    @Column(nullable = false)
    @Schema(description = "Costo de la mano de obra", example = "1500")
    private int costoManoObra;

    @Schema(description = "ID del diagnóstico asociado", example = "10")
    private Long diagnosticoId;

}