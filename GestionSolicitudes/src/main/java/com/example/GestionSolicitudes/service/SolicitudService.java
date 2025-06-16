package com.example.GestionSolicitudes.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.GestionSolicitudes.client.AsignacionClient;
import com.example.GestionSolicitudes.client.DiagnosticoClient;
import com.example.GestionSolicitudes.model.Solicitud;
import com.example.GestionSolicitudes.model.SolicitudEstado;
import com.example.GestionSolicitudes.repository.SolicitudRepository;

@Service
public class SolicitudService {

    private final SolicitudRepository solicitudRepository;

    public SolicitudService(SolicitudRepository solicitudRepository, DiagnosticoClient diagnosticoClient, AsignacionClient asignacionClient) {
        this.solicitudRepository = solicitudRepository;
    }

    public Solicitud crearS(Solicitud solicitud) {
        solicitud.setFechaCreacion(LocalDate.now());
        return solicitudRepository.save(solicitud);
    }

    public List<Solicitud> obtenerTodas() {
        return solicitudRepository.findAll();
    }

    public Optional<Solicitud> obtenerPorId(Long id) {
        return solicitudRepository.findById(id);
    }

    public Solicitud cambiarEstado(Long id, String estadoTexto) {
        Solicitud solicitud = solicitudRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Solicitud no encontrada"));

        SolicitudEstado nuevoEstado = SolicitudEstado.valueOf(estadoTexto.toUpperCase());
        solicitud.setEstado(nuevoEstado);

        return solicitudRepository.save(solicitud);
    }

    public Optional<Solicitud> actualizar(Long id, Solicitud solicitudNueva) {
        return solicitudRepository.findById(id).map(s -> {
            s.setDescripcionProblema(solicitudNueva.getDescripcionProblema());
            s.setDiagnosticoId(solicitudNueva.getDiagnosticoId());
            s.setIdAsignacion(solicitudNueva.getIdAsignacion());
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

    public List<Solicitud> filtrarPorTecnico(Long idAsignacion) {
        return solicitudRepository.findByIdAsignacion(idAsignacion);
    }
}
