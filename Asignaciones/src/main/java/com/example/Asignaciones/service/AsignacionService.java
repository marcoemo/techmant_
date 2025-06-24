package com.example.Asignaciones.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Asignaciones.model.Tecnico;
import com.example.Asignaciones.repository.AsignacionRepository;

import jakarta.annotation.PostConstruct;

@Service
public class AsignacionService {
    @Autowired
    private AsignacionRepository AR;

    private Long proximoId = 4L;

    public List<Tecnico> obtenerTodos(){
        return AR.findAll();
    }
    // Buscar por ID 
    public Tecnico buscarPorId(Long id) {
        return AR.findById(id).orElse(null);
    }

    // Eliminar 
    public void eliminarPorId(Long id) {
        if (AR.existsById(id)) {
            AR.deleteById(id);
        }
    }

    // Modificar disponibilidad por ID
    public boolean modificarDisponibilidad(Long id, String nuevaDisponibilidad) {
    Tecnico tecnico = AR.findById(id).orElse(null);
    if (tecnico != null) {
        tecnico.setDisponibilidad(nuevaDisponibilidad);
        AR.save(tecnico);
        return true;
    }
    return false;
}

    public Tecnico agregarTecnico(String nombre, String disponibilidad, Long usuarioId, Long solicitudId) {
        Tecnico nuevo = new Tecnico();
        nuevo.setIdAsignacion(proximoId++);
        nuevo.setDisponibilidad(disponibilidad);
        nuevo.setUsuarioId(usuarioId);
        nuevo.setSolicitudId(solicitudId);
        return AR.save(nuevo);
    }
    
    // Carga inicial de técnicos
    @PostConstruct
    public void init() {
        cargarTecnicosIniciales();
    }

    private void cargarTecnicosIniciales() {
    AR.deleteAll(); // Limpia la tabla antes de insertar

    List<Tecnico> tecnicosIniciales = List.of(
        crearTecnico("Disponible", 1L, 1L),
        crearTecnico("Ocupado", 2L, 2L),
        crearTecnico("Disponible", 3L, 3L)
    );

    for (Tecnico t : tecnicosIniciales) {
        AR.save(t);
    }
    System.out.println("Carga inicial de técnicos completada.");
}

private Tecnico crearTecnico(String estado, Long usuarioId, Long solicitudId) {
    Tecnico tecnico = new Tecnico();
    tecnico.setDisponibilidad(estado);
    tecnico.setUsuarioId(usuarioId);
    tecnico.setSolicitudId(solicitudId);
    return tecnico;
}
}