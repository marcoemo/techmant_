package com.example.GestionSolicitudes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.GestionSolicitudes.model.SolicitudCatalogo;
import com.example.GestionSolicitudes.model.SolicitudCatalogoId;

@Repository
public interface SolicitudCatalogoRepository extends JpaRepository<SolicitudCatalogo, SolicitudCatalogoId> {

}
