package com.example.GestionSolicitudes.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;


import com.example.GestionSolicitudes.model.Solicitud;
import com.example.GestionSolicitudes.repository.SolicitudRepository;

import jakarta.annotation.PostConstruct;

@Service
public class SolicitudService {

    private final SolicitudRepository solicitudRepository;

    public SolicitudService(SolicitudRepository solicitudRepository) {
        this.solicitudRepository = solicitudRepository;
    }

    public Solicitud agregarSolicitud(String descripcion, LocalDate fechaCreacion, Date fechaCierre, String estado, Long usuarioId) {
        Solicitud s = new Solicitud();
        s.setDescripcionProblema(descripcion);
        s.setFechaCreacion(fechaCreacion);
        s.setFechaCierre(fechaCierre);
        s.setEstado(estado);
        s.setUsuarioId(usuarioId);
        return solicitudRepository.save(s);
    }

    public List<Solicitud> obtenerTodas() {
        return solicitudRepository.findAll();
    }

    public Optional<Solicitud> obtenerPorId(Long id) {
        return solicitudRepository.findById(id);
    }

    public Optional<Solicitud> actualizar(Long id, Solicitud solicitudNueva) {
        return solicitudRepository.findById(id).map(s -> {
            s.setDescripcionProblema(solicitudNueva.getDescripcionProblema());
            s.setUsuarioId(solicitudNueva.getUsuarioId());
            s.setEstado(solicitudNueva.getEstado());
            return solicitudRepository.save(s);
        });
    }

    public boolean eliminar(Long id) {
        if (solicitudRepository.existsById(id)) {
            solicitudRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Solicitud> filtrarPorUsuario(Long usuarioId) {
        return solicitudRepository.findByUsuarioId(usuarioId);
    }


    @PostConstruct
    public void init() {
        cargarSolicitudesIniciales();
    }

    private void cargarSolicitudesIniciales() {
        if (solicitudRepository.count() == 0) {
            agregarSolicitud("No prende el equipo", LocalDate.now(), null, "Pendiente", 1L);
            agregarSolicitud("Error en pantalla", LocalDate.now(), null, "En proceso", 2L);
            agregarSolicitud("Revisar conexión", LocalDate.now(), null, "Cerrado", 3L);
        }
    }

}
