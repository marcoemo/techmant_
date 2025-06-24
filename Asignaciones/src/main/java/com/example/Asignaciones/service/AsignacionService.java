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
    public void modificarDisponibilidad(Long id, String nuevaDisponibilidad) {
        Tecnico tecnico = AR.findById(id).orElse(null);
        if (tecnico != null) {
            tecnico.setDisponibilidad(nuevaDisponibilidad);
            AR.save(tecnico);
        }
    }

    public Tecnico agregarTecnico(String nombre, String disponibilidad, Long usuarioId, Long solicitudId) {
        Tecnico nuevo = new Tecnico();
        nuevo.setIdAsignacion(proximoId++);
        nuevo.setDisponibilidad(disponibilidad);
        nuevo.setUsuarioId(usuarioId);
        nuevo.setSolicitudId(solicitudId);
        return AR.save(nuevo);
    }
    
    //creacion de datos guardados
    @PostConstruct
    public void cargarDatosIniciales() {
    try {
        if (AR.count() == 0) {
            AR.save(new Tecnico(1L, 1L, "Disponible", 1L));
            AR.save(new Tecnico(2L, 2L, "Ocupado", 2L));
            AR.save(new Tecnico(3L, 3L, "Disponible", 3L));
            System.out.println("Técnicos iniciales cargados correctamente.");
        } else {
            System.out.println("Ya existen técnicos en la base de datos. No se cargaron datos iniciales.");
        }
    } catch (Exception e) {
        System.err.println("Error al cargar técnicos iniciales: " + e.getMessage());
        e.printStackTrace();
        }
    }
}