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
    
    //Crear una nueva solicitud con la fecha actual
    public Solicitud crearS(Solicitud solicitud) {
        solicitud.setFechaCreacion(LocalDate.now());
        return solicitudRepository.save(solicitud);
    }

    //obtener todas las solicitudes
    public List<Solicitud> obtenerTodas() {
        return solicitudRepository.findAll();
    }

    //obtener una solicitud por id
    public Optional<Solicitud> obtenerPorId(Long id) {
        return solicitudRepository.findById(id);
    }

    //Cambiar estado de la solicitud
    public Solicitud cambiarEstado(Long id, String estadoTexto) {
    Solicitud solicitud = solicitudRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Solicitud no encontrada"));

    SolicitudEstado nuevoEstado = SolicitudEstado.valueOf(estadoTexto.toUpperCase());
    solicitud.setEstado(nuevoEstado);

    return solicitudRepository.save(solicitud);
    }

}
