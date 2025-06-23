package com.example.Diagnosticos.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "diagnosticos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Diagnostico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long diagnosticoId;

    @Column(length = 1000)
    private String detalle;
    
    @Column(nullable = false, length = 100)
    private String estado;

    @Column(nullable = false)
    private LocalDateTime fechaDiagnostico;
    
    private Long IdAsignacion;
}

