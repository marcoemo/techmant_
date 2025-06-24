package com.example.Autenticacion.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name ="Autenticacion")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Auth del sistema")
public class Auth {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Schema(description = "Identificador único del usuario", example = "1")
   private Long id;

   @Column(nullable = false, unique = true)
   @Schema(description = "Correo electrónico del usuario", example = "lore@gmail.com")
   private String correo;

   @Column(nullable = false)
   @Schema(description = "Contraseña del usuario", example = "abc12s3")
   private String contrasena;

   /*
    * {
      "correo": "lore@gmail.com",
       "contrasena": "abc12s3"
      }
    */
}