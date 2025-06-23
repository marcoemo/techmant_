package com.example.Seguimiento.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "seguimiento")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Entidad que representa el seguimiento de una solicitud de soporte")
public class Seguimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_seguimiento")
    @Schema(description = "ID único del seguimiento", example = "1")
    private Long idSeguimiento;

    @Column(name = "estado_seguimiento", nullable = false)
    @Schema(description = "Estado actual del seguimiento", example = "En diagnóstico", required = true)
    private String estado;

    @Column(name = "observaciones")
    @Schema(description = "Observaciones adicionales sobre el estado del seguimiento", example = "Se detectaron fallos en la placa madre")
    private String observaciones;

    @Column(name = "solicitud_id")
    @Schema(description = "ID de la solicitud a la que pertenece este seguimiento", example = "3", required = true)
    private Long solicitudId;

    /*
      Ejemplo JSON:
      {
        "estado": "En diagnóstico",
        "observaciones": "Se detectaron fallos en la placa madre",
        "solicitudId": 3
      }
    */
}
