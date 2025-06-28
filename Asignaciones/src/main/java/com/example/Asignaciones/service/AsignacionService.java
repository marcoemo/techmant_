package com.example.Asignaciones.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Asignaciones.model.Tecnico;
import com.example.Asignaciones.repository.AsignacionRepository;

import jakarta.annotation.PostConstruct;

@Service
public class AsignacionService {
    @Autowired
    private AsignacionRepository AR;

    public List<Tecnico> obtenerTodos() {
        return AR.findAll();
    }

    // Buscar técnico por ID
    public Tecnico buscarPorId(Long id) {
        if (id == null) {
            return null;
        }
        return AR.findById(id).orElse(null);
    }

    // Obtener técnicos con información de usuario y solicitud
    public List<Map<String, Object>> obtenerTecnicosConUsuarioYSolicitud() {
        List<Tecnico> tecnicos = AR.findAll();
        List<Map<String, Object>> resultado = new java.util.ArrayList<>();
        for (Tecnico tecnico : tecnicos) {
            Map<String, Object> map = new java.util.HashMap<>();
            map.put("tecnico", tecnico);
            map.put("usuarioId", tecnico.getUsuarioId());
            map.put("solicitudId", tecnico.getSolicitudId());
            resultado.add(map);
        }
        return resultado;
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

    public Tecnico agregarTecnico(String disponibilidad, Long usuarioId, Long solicitudId) {
        // Verifica si ya existe un técnico con el mismo idAsignacion (usuarioId + solicitudId)
        boolean existe = AR.findAll().stream()
            .anyMatch(t -> t.getUsuarioId().equals(usuarioId) && t.getSolicitudId().equals(solicitudId));
        if (existe) {
            return null; 
        }
        Tecnico nuevo = new Tecnico();
        nuevo.setDisponibilidad(disponibilidad);
        nuevo.setUsuarioId(usuarioId);
        nuevo.setSolicitudId(solicitudId);
        return AR.save(nuevo);
    }

    @PostConstruct
    public void init() {
    List<Tecnico> tecnicosIniciales = List.of(
        crearTecnico("Disponible", 1L, 1L),
        crearTecnico("Ocupado", 2L, 2L),
        crearTecnico("Disponible", 3L, 3L)
    );

    crearTecnicosSiNoExisten(tecnicosIniciales);
    }

    private Tecnico crearTecnico(String disponibilidad, Long usuarioId, Long solicitudId) {
    Tecnico tecnico = new Tecnico();
    tecnico.setDisponibilidad(disponibilidad);
    tecnico.setUsuarioId(usuarioId);
    tecnico.setSolicitudId(solicitudId);
    return tecnico;
}

    private void crearTecnicosSiNoExisten(List<Tecnico> tecnicos) {
    int creados = 0;

    for (Tecnico tecnico : tecnicos) {
        // Verificamos si ya existe un técnico con ese usuarioId
        boolean existe = AR.existsById(tecnico.getUsuarioId());
        if (!existe) {
            AR.save(tecnico);
            creados++;
            System.out.println("Técnico creado: usuarioId " + tecnico.getUsuarioId());
        }
    }

    System.out.println("Total técnicos creados: " + creados + "/" + tecnicos.size());
    }   
}
