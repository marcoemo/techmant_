package com.example.Respaldo.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "respaldos")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Respaldo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre_archivo", nullable = false)
    private String nombreArchivo;

    @Column(name = "fecha_respaldo", nullable = false)
    private LocalDateTime fechaRespaldo;

    @Column(name = "exito", nullable = false)
    private boolean exito;

    @Column(name = "mensaje")
    private String mensaje;
}