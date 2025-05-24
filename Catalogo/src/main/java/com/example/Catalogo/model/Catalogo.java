package com.example.Catalogo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="Catalogo")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Catalogo {

        @Id
        @Column
        private Long idCatalogo;

        @Column(name="nombre_servicio",nullable = false)
        private String nombre;


        @Column(name="descripcion_servicio",nullable = false)
        private String descripcion;
        
        @Column(name="precio",nullable = false)
        private Integer precio;


}
