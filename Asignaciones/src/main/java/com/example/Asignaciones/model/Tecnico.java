package com.example.Asignaciones.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Table(name = "Tecnico")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Tecnico {
    @Id
    @Column
    private Long IdAsignacion;

    @Column(name = "NombreTecnico", nullable = false)
    private String NombreTecnico;

    @Column(name = "Estado(Disponible/Ocupado)",nullable = false)
    private String disponibilidad;

    public String getDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(String nuevaDisponibilidad) {
        this.disponibilidad = nuevaDisponibilidad;
    }
}

/*
 {
  "nombre": "pabloooooou",
  "disponibilidad": "ocupado"
}

{
  "nuevaDisponibilidad": "disponible"
}
 */
