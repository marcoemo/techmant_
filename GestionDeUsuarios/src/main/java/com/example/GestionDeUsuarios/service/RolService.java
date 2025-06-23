package com.example.GestionDeUsuarios.service;

import com.example.GestionDeUsuarios.model.Rol;
import com.example.GestionDeUsuarios.repository.RolRepository;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RolService {

    @Autowired
    private final RolRepository RP;

    public RolService(RolRepository RP) {
        this.RP = RP;
    }

    //  Obtener todos los roles
    public List<Rol> obtenerTodos() {
        return RP.findAll();
    }

    //  Crear nuevo rol
    public Rol guardarRol(Rol rol) {
        return RP.save(rol);
    }

    //  Obtener rol por ID
    public Optional<Rol> obtenerPorId(Long id) {
        return RP.findById(id);
    }

    //  Actualizar rol existente
    public Optional<Rol> actualizarRol(Long id, Rol datos) {
        return RP.findById(id).map(rol -> {
            rol.setNombreRol(datos.getNombreRol());
            return RP.save(rol);
        });
    }

    // Eliminar rol por ID
    public boolean eliminarRol(Long id) {
        if (RP.existsById(id)) {
            RP.deleteById(id);
            return true;
        }
        return false;
    }

    //  Crear roles base al iniciar el microservicio
    @PostConstruct
    public void CargarRolesIniciales() {
        if (!RP.existsById(1L)) RP.save(new Rol(1L, "ADMINISTRADOR"));
        if (!RP.existsById(2L)) RP.save(new Rol(2L, "TECNICO"));
        if (!RP.existsById(3L)) RP.save(new Rol(3L, "SUPERVISOR"));
        if (!RP.existsById(4L)) RP.save(new Rol(4L, "SOPORTE"));
        if (!RP.existsById(5L)) RP.save(new Rol(5L, "USUARIO"));
    }
}
