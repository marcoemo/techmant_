package com.example.GestionDeUsuarios.model;

import jakarta.persistence.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad que representa un Rol dentro del sistema.
 */
@Entity
@Table(name = "Rol")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Rol {

    @Id
    @Column(name = "IdRol")
    @Schema(description = "ID único del rol", example = "1")
    private long idRol;

    @Column(name = "NombreDelRol", nullable = false)
    @Schema(description = "Nombre descriptivo del rol", example = "ADMINISTRADOR")
    private String NombreRol;
}