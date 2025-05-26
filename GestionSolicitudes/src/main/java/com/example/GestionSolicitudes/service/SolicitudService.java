package com.example.GestionSolicitudes.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
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
    private final DiagnosticoClient diagnosticoClient;
    private final AsignacionClient asignacionClient;


    public SolicitudService(SolicitudRepository solicitudRepository, DiagnosticoClient diagnosticoClient, AsignacionClient asignacionClient) {
        this.solicitudRepository = solicitudRepository;
        this.diagnosticoClient = diagnosticoClient;
        this.asignacionClient = asignacionClient;
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


    
    //obtener una solicitud con su tecnico REVISAR BIEN
        public Map<String, Object> obtenerSolicitudConTecnico(Long solicitudId) {
        Solicitud solicitud = solicitudRepository.findById(solicitudId).orElse(null);
        if (solicitud == null) return null;

        Map<String, Object> tecnico = asignacionClient.obtenerAsigancionPorId(solicitud.getIdAsignacion());

        return Map.of(
            "solicitud", solicitud,
            "tecnico", tecnico
        );
        }

    //obtener una solicitud con su diagnostico REVISAR BIEN
        public Map<String, Object> obtenerDetalleSolicitudConDiagnostico(Long solicitudId) {
            var solicitud = solicitudRepository.findById(solicitudId).orElse(null);
            if (solicitud == null) return null;

            Map<String, Object> diagnostico = diagnosticoClient.obtenerDiagnosticoPorId(solicitud.getDiagnosticoId());

            return Map.of(
                "solicitud", solicitud,
                "diagnostico", diagnostico
            );
        }
}
