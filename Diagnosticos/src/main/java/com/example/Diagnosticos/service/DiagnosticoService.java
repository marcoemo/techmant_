package com.example.Diagnosticos.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.Diagnosticos.model.Diagnostico;
import com.example.Diagnosticos.repository.DiagnosticoRepository;

import jakarta.annotation.PostConstruct;

@Service
public class DiagnosticoService {

    private final DiagnosticoRepository diagRepo;

    public DiagnosticoService(DiagnosticoRepository diagRepo) {
        this.diagRepo = diagRepo;
    }
    // Crear un nuevo diagnóstico
    public Diagnostico agregarDiagnostico(String detalle, String estado, 
                                LocalDateTime localDateTime,Long IdAsignacion) {
        Diagnostico nuevo = new Diagnostico();
        nuevo.setDetalle(detalle);
        nuevo.setEstado(estado);
        nuevo.setFechaDiagnostico(localDateTime);
        nuevo.setIdAsignacion(IdAsignacion);
        return diagRepo.save(nuevo);
    }
    
    // obtener todos los diagnósticos
    public List<Diagnostico> obtenerTodos() {
        return diagRepo.findAll();
    }
    // obtener un diagnóstico por id
    public Diagnostico obtenerPorId(Long id) {
        return diagRepo.findById(id).orElse(null);
    }
    // Actualizar un diagnóstico
    public Diagnostico actualizar(Long id, Diagnostico nuevo) {
        Diagnostico existente = diagRepo.findById(id).orElse(null);
        if (existente != null) {
            existente.setDetalle(nuevo.getDetalle());
            existente.setEstado(nuevo.getEstado());
            existente.setFechaDiagnostico(nuevo.getFechaDiagnostico());
            return diagRepo.save(existente);
        }
        return null;
    }
    //eliminar un diagnóstico por id
    public void eliminarPorId(Long id) {
        if (diagRepo.existsById(id)) {
            diagRepo.deleteById(id);
        }
    }

    @PostConstruct
    public void init() {
        cargarDiagnosticoIniciales();
    }

    private void cargarDiagnosticoIniciales() {
        List<Diagnostico> diagnosticosIniciales = List.of(
            agregarDiagnostico("No procesa bien", "Disponible", LocalDateTime.now(), 1L),
            agregarDiagnostico("Lentitud de carga", "No disponible", LocalDateTime.now(), 2L),
            agregarDiagnostico("Sistema interno dañado", "En espera", LocalDateTime.now(), 3L)
        );
        
        for (Diagnostico diagnostico : diagnosticosIniciales) {
            // Verificar si ya existe por ID
            if (!diagRepo.existsById(diagnostico.getIdAsignacion())) {
                diagRepo.save(diagnostico);
            }
        }
    }
}
