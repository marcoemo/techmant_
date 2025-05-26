package com.example.Diagnosticos.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.Diagnosticos.model.Equipo;
import com.example.Diagnosticos.repository.EquipoRepository;

@Service
public class EquipoService {
    private final EquipoRepository equipRepo;

    public EquipoService(EquipoRepository equipRepo) {
        this.equipRepo = equipRepo;
    }
    // Crear un nuevo equipo
    public Equipo crear(Equipo e) {
        return equipRepo.save(e);
    }
    // obtener todos los equipos
    public List<Equipo> obtenerTodos() {
        return equipRepo.findAll();
    }
    // obtener un equipo por id
    public Equipo obtenerPorId(Long id) {
        return equipRepo.findById(id).orElse(null);
    }
    // obtener equipos por id de usuario
    public List<Equipo> buscarPorUsuario(Long usuarioId) {
        return equipRepo.findAll().stream()
                .filter(e -> e.getUsuarioId() != null && e.getUsuarioId().equals(usuarioId))
                .toList();
    }
    //metdo eliminar ??
}
