package com.example.GestionSolicitudes.model;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SolicitudCatalogoId implements Serializable {
    
    private Long solicitudId; //fk solicitud
    private Long idCatalogo; //fk servicio
}
