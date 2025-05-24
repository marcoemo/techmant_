package com.example.Resenas.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="Resenias")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Resenas {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name="IdResenia")
        private Long idResena;

        @Column(name="DescripcionResenia")
        private String resena;

        @Column(name="Calificacion(1-5)",nullable = false)
        private Integer calificacion;


        /*
             {
                "resena": "Muy buen servicio, el técnico fue muy amable",
                "calificacion": 5
            }
         */
}
