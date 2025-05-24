package com.example.Autenticacion.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name ="Autenticacion")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Auth {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String correo;

    @Column(nullable = false)
    private String contrasena;

    /*
     * {
        "correo": "lore@gmail.com",
         "contrasena": "abc12s3"
        }
     */
}