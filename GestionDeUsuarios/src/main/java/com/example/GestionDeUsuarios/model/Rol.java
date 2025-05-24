package com.example.GestionDeUsuarios.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Rol")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Rol {
    @Id
    @Column(name="IdRol")
    private long idRol;

    @Column(name = "NombreDelRol", nullable = false)
    private String NombreRol;
}
