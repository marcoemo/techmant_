package com.example.Diagnosticos.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "diagnosticos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Diagnostico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long diagnosticoId;

    @ManyToOne
    @JoinColumn(name = "equipo_id")
    private Equipo equipo;

    @Column(length = 1000)
    private String detalle;
    
    @Enumerated(EnumType.STRING)
    private EstadoDiagnostico estado;

    @Column(nullable = false)
    private LocalDate fechaDiagnostico;

    @Column(nullable = false)
    private int costoManoObra;
}

enum EstadoDiagnostico {
    PENDIENTE,
    COMPLETADO
}  