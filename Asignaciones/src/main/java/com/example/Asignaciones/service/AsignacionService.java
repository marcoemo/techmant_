package com.example.Asignaciones.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Asignaciones.model.Tecnico;
import com.example.Asignaciones.repository.AsignacionRepository;

@Service
public class AsignacionService {
    @Autowired
    private AsignacionRepository AR;

     private Long proximoId = 4L;

    public void cargarTecnicosIniciales(){
        if (AR.count() == 0) {
            AR.save(new Tecnico(1L, "Ricardo","Disponible"));
            AR.save(new Tecnico(2L, "Dariel","Ocupado"));
            AR.save(new Tecnico(3L, "Carlos","Disponible"));
            
        }
    }

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
    public Tecnico agregarTecnico(String nombre, String disponibilidad) {
        Tecnico nuevo = new Tecnico(proximoId, nombre, disponibilidad);
        AR.save(nuevo);
        proximoId++;
        return nuevo;
    }
}