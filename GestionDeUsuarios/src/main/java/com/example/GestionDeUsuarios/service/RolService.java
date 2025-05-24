package com.example.GestionDeUsuarios.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.GestionDeUsuarios.model.Rol;
import com.example.GestionDeUsuarios.repository.RolRepository;

import jakarta.annotation.PostConstruct;

@Service
public class RolService {
    @Autowired
    private final RolRepository RP;

    public RolService(RolRepository RP) {
        this.RP = RP;
    }

    @PostConstruct
    public void CargarRolesIniciales() {
        if (!RP.existsById(1L)) {
            RP.save(new Rol(1L, "ADMINISTRADOR"));
        }
        if (!RP.existsById(2L)) {
            RP.save(new Rol(2L, "TECNICO"));
        }
        if (!RP.existsById(3L)) {
            RP.save(new Rol(3L, "SUPERVISOR"));
        }
        if (!RP.existsById(4L)) {
            RP.save(new Rol(4L, "SOPORTE"));
        }
        if (!RP.existsById(5L)) {
            RP.save(new Rol(5L, "USUARIO"));
        }
    }
      public List<Rol> obtenerTodos() {
        return RP.findAll();
    }

}
