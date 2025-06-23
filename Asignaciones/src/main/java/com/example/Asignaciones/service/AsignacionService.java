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
    public void init() {
        cargarTecnicosIniciales();
    }
    
    private void cargarTecnicosIniciales() {
        List<Tecnico> tecnicosIniciales = List.of(
            crearTecnico(1L, "Pedro", "Disponible", 1L, 1L),
            crearTecnico(2L, "María", "Ocupado", 2L, 2L),
            crearTecnico(3L, "Carlos", "Disponible", 3L, 3L)
        );
        
        for (Tecnico tecnico : tecnicosIniciales) {
            // Verificar si ya existe por ID
            if (!AR.existsById(tecnico.getIdAsignacion())) {
                AR.save(tecnico);
            }
        }
    }

    private Tecnico crearTecnico(Long id, String nombre, String estado, 
                                Long usuarioId, Long solicitudId) {
        Tecnico tecnico = new Tecnico();
        tecnico.setIdAsignacion(id);
        tecnico.setDisponibilidad(estado);
        tecnico.setUsuarioId(usuarioId);
        tecnico.setSolicitudId(solicitudId);
        return tecnico;
    }

    public Tecnico crearTecnicoConId(Long id, String nombre, String estado, 
                                   Long usuarioId, Long solicitudId) {
        // Verificar si el ID ya existe
        if (AR.existsById(id)) {
            throw new RuntimeException("El ID " + id + " ya está en uso");
        }
        
        Tecnico nuevo = crearTecnico(id, nombre, estado, usuarioId, solicitudId);
        return AR.save(nuevo);
    }
    //termino de carga
}