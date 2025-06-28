package com.example.Asignaciones.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Table(name = "Tecnico")
@Data
@AllArgsConstructor
@NoArgsConstructor

@Schema(description = "Modelo de tecnico")
public class Tecnico {

  @Id
  @Column
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Schema(description = "Identificador único de la asignación", example = "1")
  private Long idAsignacion;

  @Schema(description = "Identificador único del usuario asociado al técnico", example = "1001")
  private Long usuarioId;

  @Column(name = "Estado(Disponible/Ocupado)", nullable = false)
  @Schema(description = "Disponibilidad del técnico (Disponible/Ocupado)", example = "Disponible")
  private String disponibilidad;

  @Schema(description = "Identificador de la solicitud asignada al técnico", example = "2002")
  private Long solicitudId;

  public String getDisponibilidad() {
    return disponibilidad;
  }

  public void setDisponibilidad(String nuevaDisponibilidad) {
    this.disponibilidad = nuevaDisponibilidad;
  }

  
}
