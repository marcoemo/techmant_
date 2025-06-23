package com.example.GestionDeUsuarios.model;

import jakarta.persistence.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad que representa un Usuario en el sistema.
 */
@Entity
@Table(name = "Usuario")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Schema(description = "Modelo usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdUsuario")
    @Schema(description = "ID único generado automáticamente para el usuario", example = "1")
    private Long idUsuario;

    @Column(name = "Nombre", nullable = false)
    @Schema(description = "Nombre completo del usuario", example = "Juan Pérez")
    private String nombre;

    @Column(name = "Correo", nullable = false)
    @Schema(description = "Correo electrónico del usuario", example = "juan.perez@ejemplo.com")
    private String correo;

    @Column(name = "Contraseña", nullable = false)
    @Schema(description = "Contraseña cifrada del usuario", example = "clave123")
    private String contrasena;

    @ManyToOne
    @JoinColumn(name = "IdRol", nullable = false)
    @Schema(description = "Rol asignado al usuario")
    private Rol rol;

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
}

/*
Ejemplo JSON para creación o actualización de Usuario:

{
  "nombre": "Juan Pérez",
  "correo": "juan.perez@ejemplo.com",
  "contrasena": "clave123",
  "rol": {
    "idRol": 5,
    "nombreRol": "USUARIO"
  }
}

Roles estándar:
1 → ADMINISTRADOR
2 → TÉCNICO
3 → SUPERVISOR
4 → SOPORTE
5 → USUARIO
*/
