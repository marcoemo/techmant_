package com.example.GestionSolicitudes.model;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "ID compuesto para la entidad SolicitudCatalogo")
public class SolicitudCatalogoId implements Serializable {
    
    @Schema(description = "Identificador único de la solicitud")
    private Long solicitudId; //fk solicitud
    @Schema(description = "Identificador del catálogo de servicios asociado")
    private Long idCatalogo; //fk servicio
}
