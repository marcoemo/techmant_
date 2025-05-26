package com.example.Reportes.model;

import java.time.LocalDate;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "reportes_tecnico")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reporteId;

    @Min(1)
    @Max(10)
    @Column(nullable = false)
    private int calificacion; // del 1 al 10

    @Column(length = 500, nullable = false)
    private String observaciones;

    @Column(length = 500, nullable = false)
    private String aspectoDestacado;

    @Column(nullable = false)
    private LocalDate fechaGeneracion;

}