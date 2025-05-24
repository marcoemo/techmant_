package com.example.Seguimiento.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "seguimiento")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Seguimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_seguimiento")
    private Long idSeguimiento;

    @Column(name = "estado_seguimiento", nullable = false)
    private String estado;

    @Column(name = "observaciones")
    private String observaciones;

    @Column(name = "solicitud_id")
    private Long solicitudId; // es solo el ID, no se hace relación directa

    /*
      {
        "estado": "En diagnóstico",
        "observaciones": "Se detectaron fallos en la placa madre",
        "solicitudId": 3
      }
     */

}