package com.example.Resenas.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Table(name = "Resenias")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Resenas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdResenia")
    private Long idResena;

    @Column(name = "DescripcionResenia", nullable = false)
    private String resena;

    @Column(name = "Calificacion", nullable = false)
    private Integer calificacion;

    @Column(name = "usuario_id", nullable = false)
    private Long usuarioId;
}

        /*
             {
                "resena": "Muy buen servicio, el técnico fue muy amable",
                "calificacion": 5
            }
         */