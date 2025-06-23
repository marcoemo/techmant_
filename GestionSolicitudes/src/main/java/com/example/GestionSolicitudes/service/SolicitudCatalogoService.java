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

import jakarta.annotation.PostConstruct;

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

    public SolicitudCatalogo agregarRelacion(Long solicitudId, Long catalogoId, int subtotal) {
        Solicitud solicitud = solRepo.findById(solicitudId).orElse(null);
        if (solicitud == null) return null;

        SolicitudCatalogoId id = new SolicitudCatalogoId(solicitudId, catalogoId);
        SolicitudCatalogo sc = new SolicitudCatalogo();
        sc.setId(id);
        sc.setSolicitud(solicitud);
        sc.setSubtotal(subtotal);

        return solCatRepo.save(sc);
    }

    //inicializar datos
    public void init() {
        cargarRelacionesIniciales();
    }

    @PostConstruct
    private void cargarRelacionesIniciales() {
        guardarSiNoExiste(1L, 1L, 1200);
        guardarSiNoExiste(2L, 2L, 2500);
        guardarSiNoExiste(3L, 3L, 1800);
    }

    public void guardarSiNoExiste(Long solicitudId, Long catalogoId, int subtotal) {
        SolicitudCatalogoId id = new SolicitudCatalogoId(solicitudId, catalogoId);
        if (!solCatRepo.existsById(id)) {
            agregarRelacion(solicitudId, catalogoId, subtotal);
        }
    }

    // Eliminar una relación por su id compuesto
    public void eliminarPorId(SolicitudCatalogoId id) {
        if (solCatRepo.existsById(id)) {
            solCatRepo.deleteById(id);
        } else {
            throw new IllegalArgumentException("Relación no encontrada");
        }
    }
}