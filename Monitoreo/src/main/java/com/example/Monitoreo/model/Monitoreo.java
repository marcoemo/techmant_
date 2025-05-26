package com.example.Monitoreo.model;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "eventos_monitoreo")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Monitoreo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eventoId;

    private String servicio;

    @Enumerated(EnumType.STRING)
    private TipoEvento tipo;

    private String descripcion;

    private LocalDate fechaEvento;

    private boolean resuelto;
}

enum TipoEvento {
    OK,
    CAIDA,
    RETRASO,
    ALERTA
}