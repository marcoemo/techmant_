package com.example.Seguimiento.service;

import com.example.Seguimiento.model.Seguimiento;
import com.example.Seguimiento.repository.SeguimientoRepository;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SeguimientoService {

    @Autowired
    private SeguimientoRepository repo;

    public List<Seguimiento> obtenerTodos() {
        return repo.findAll();
    }

    public Optional<Seguimiento> obtenerPorId(Long id) {
        return repo.findById(id);
    }

    public Seguimiento crear(Seguimiento s) {
        return repo.save(s);
    }

    public Optional<Seguimiento> actualizar(Long id, Seguimiento nuevo) {
        return repo.findById(id).map(s -> {
            s.setEstado(nuevo.getEstado());
            s.setObservaciones(nuevo.getObservaciones());
            s.setSolicitudId(nuevo.getSolicitudId());
            return repo.save(s);
        });
    }

    public void eliminar(Long id) {
        repo.deleteById(id);
    }

    public List<Seguimiento> filtrarPorSolicitud(Long solicitudId) {
        return repo.findBySolicitudId(solicitudId);
    }

    @PostConstruct
    public void cargarSeguimientosIniciales() {
        guardarSiNoExiste("En revisión", "Se está analizando el problema", 1L);
        guardarSiNoExiste("Esperando repuestos", "Se enviará a taller", 2L);
        guardarSiNoExiste("Finalizado", "Equipo reparado y entregado", 3L);
    }

    private void guardarSiNoExiste(String estado, String observaciones, Long solicitudId) {
    boolean yaExiste = repo
        .findAll()
        .stream()
        .anyMatch(s -> s.getSolicitudId().equals(solicitudId));

    if (!yaExiste) {
        Seguimiento s = new Seguimiento();
        s.setEstado(estado);
        s.setObservaciones(observaciones);
        s.setSolicitudId(solicitudId);
        repo.save(s);
        }
    }
}
