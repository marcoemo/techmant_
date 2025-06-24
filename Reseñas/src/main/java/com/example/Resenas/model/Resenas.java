package com.example.Resenas.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Resenias")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Entidad que representa una reseña realizada por un usuario sobre un servicio o experiencia.")
public class Resenas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdResenia")
    @Schema(description = "ID único de la reseña", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long idResena;

    @Column(name = "tipo_resena", nullable = false)
    @Schema(
        description = "Tipo de reseña realizada por el usuario. Solo se permiten: 'positiva', 'neutra', o 'negativa'.",
        example = "positiva",
        required = true
    )
    private String tipoResena;

    @Column(name = "usuario_id", nullable = false)
    @Schema(description = "ID del usuario que realizó la reseña", example = "10", required = true)
    private Long usuarioId;
}
