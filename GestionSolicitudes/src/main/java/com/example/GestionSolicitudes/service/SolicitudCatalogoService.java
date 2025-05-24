package com.example.GestionSolicitudes.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.GestionSolicitudes.client.CatalogoClient;
import com.example.GestionSolicitudes.model.Solicitud;
import com.example.GestionSolicitudes.model.SolicitudCatalogo;
import com.example.GestionSolicitudes.model.SolicitudCatalogoId;
import com.example.GestionSolicitudes.repository.SolicitudCatalogoRepository;
import com.example.GestionSolicitudes.repository.SolicitudRepository;

@Service
public class SolicitudCatalogoService {

    private final SolicitudCatalogoRepository solCatRepo;
    private final CatalogoClient catalogoClient;
    private final SolicitudRepository solRepo;

    public SolicitudCatalogoService(SolicitudCatalogoRepository solCatRepo, SolicitudRepository solRepo, CatalogoClient catalogoClient) {
        this.solCatRepo = solCatRepo;
        this.solRepo = solRepo;
        this.catalogoClient = catalogoClient;
    }

    public SolicitudCatalogo crearV(SolicitudCatalogo relacion) {
    Long solicitudId = relacion.getId().getSolicitudId();
    Long idCatalogo = relacion.getId().getIdCatalogo();

    // Validar solicitud
    Solicitud solicitud = solRepo.findById(solicitudId)
            .orElseThrow(() -> new IllegalArgumentException("Solicitud no encontrada"));

    // Validar llamando al microservicio Catalogo
    Map<String, Object> catalogo = catalogoClient.obtenerCatalogoPorId(idCatalogo);
    if (catalogo == null || catalogo.isEmpty()) {
        throw new IllegalArgumentException("Catálogo no encontrado");
    }

    relacion.setSolicitud(solicitud);

    return solCatRepo.save(relacion);
}

    //listar todos los vinculos
    public List<SolicitudCatalogo> obtenerTodos() {
        return solCatRepo.findAll();
    }

    //busca relacion especifica por su clave compuesta
    public Optional<SolicitudCatalogo> obtenerPorId(SolicitudCatalogoId id) {
        return solCatRepo.findById(id);
    }

}
