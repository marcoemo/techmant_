package com.example.Equipo.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "equipos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Modelo de Equipo del Cliente")
public class Equipo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único del equipo")
    private Long equipoId;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    @Schema(description = "Categoría a la que pertenece el equipo")
    private Categoria categoria;

    @Column(nullable = false, length = 100)
    @Schema(description = "Marca del equipo")
    private String marca;

    @Column(nullable = false, length = 100)
    @Schema(description = "Modelo del equipo")
    private String modelo;

    @Column(nullable = false, length = 100)
    @Schema(description = "Número de serie del equipo")
    private String numeroSerie;

    @Schema(description = "Identificador del usuario propietario del equipo")
    private Long idUsuario;
}
