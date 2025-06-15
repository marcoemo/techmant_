package com.example.GestionDeUsuarios.model;

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
@Table(name="Usuario")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Usuario {
    @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name="IdUsuario")
   private Long idUsuario;

   @Column(name= "Nombre",nullable = false)
   private String nombre;

   @Column(name="Correo",nullable = false)
   private String correo;

   @Column(name="Contraseña",nullable = false)
   private String contrasena;
   
   @ManyToOne
   @JoinColumn(name = "IdRol", nullable = false)
   private Rol rol;
   public String getContrasena() {
    return contrasena;
    }

    public void setContrasena(String contrasena) {
    this.contrasena = contrasena;
    }
}

/*
{
  {
  "nombre": "Juan Pérez",
  "correo": "juan.perez@ejemplo.com",
  "contrasena": "clave123",
  "rol": {
    "idRol": 5,
    "nombreRol": "USUARIO"
  }
}
  1 → ADMINISTRADOR

  2 → TÉCNICO

  3 → SUPERVISOR

  4 → SOPORTE

  5 → USUARIO
 */