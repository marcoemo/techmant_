package com.example.Reportes.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.Reportes.model.Reporte;
import com.example.Reportes.repository.ReporteRepository;

import jakarta.annotation.PostConstruct;

@Service
public class ReporteService {
    private final ReporteRepository repo;

    public ReporteService(ReporteRepository repo) {
        this.repo = repo;
    }
    
    //crear un nuevo reporte -- cliente
    public Reporte agregarReporte(LocalDate fecha, int costoManoObra,Long diagnosticoId ) {
        Reporte r = new Reporte();
        r.setFechaGeneracion(fecha);
        r.setCostoManoObra(costoManoObra);
        r.setDiagnosticoId(diagnosticoId);
        return repo.save(r);
    }

    //obtener todos los reportes -- cliente -- admin -- tencnico
    public List<Reporte> obtenerTodos() {
        return repo.findAll();
    }

    //obtener un reporte por id -- cliente -- tecnico -- admin
    public Reporte obtenerPorId(Long id) {
        return repo.findById(id).orElse(null);
    }

    //eliminar un reporte por id -- admin -- cliente
    public void eliminarPorId(Long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
        }
    }

    @PostConstruct
    public void init() {
        cargarReportesIniciales();
    }

    private void cargarReportesIniciales() {
        if (repo.count() == 0) { 
            agregarReporte(LocalDate.now(),10000,1L);
            agregarReporte(LocalDate.now(),200000,2L);
            agregarReporte( LocalDate.now(),300000,3L);
        }
    }
}