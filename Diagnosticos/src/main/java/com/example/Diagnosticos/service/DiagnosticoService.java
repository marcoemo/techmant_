package com.example.Diagnosticos.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.Diagnosticos.model.Diagnostico;
import com.example.Diagnosticos.repository.DiagnosticoRepository;

@Service
public class DiagnosticoService {

    private final DiagnosticoRepository diagRepo;

    public DiagnosticoService(DiagnosticoRepository diagRepo) {
        this.diagRepo = diagRepo;
    }
    // Crear un nuevo diagnóstico
    public Diagnostico crear(Diagnostico d) {
    d.setFechaDiagnostico(LocalDate.now());
    return diagRepo.save(d);
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
            existente.setCostoManoObra(nuevo.getCostoManoObra());
            return diagRepo.save(existente);
        }
        return null;
    }
    // metodo eliminar ??
}
