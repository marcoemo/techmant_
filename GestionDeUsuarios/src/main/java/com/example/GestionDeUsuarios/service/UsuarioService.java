package com.example.GestionDeUsuarios.service;

import com.example.GestionDeUsuarios.model.Rol;
import com.example.GestionDeUsuarios.model.Usuario;
import com.example.GestionDeUsuarios.repository.RolRepository;
import com.example.GestionDeUsuarios.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private final UsuarioRepository UR;

    @Autowired
    private RolRepository rolRepository;

    private final PasswordEncoder PE;

    public UsuarioService(UsuarioRepository UR, PasswordEncoder PE) {
        this.UR = UR;
        this.PE = PE;
    }

    // Crear usuario con validación de correo duplicado
    public String guardarUsuario(Usuario usu) {
        if (UR.existsByCorreo(usu.getCorreo())) {
            return "Ya existe un usuario con ese correo";
        }
        usu.setContrasena(PE.encode(usu.getContrasena()));
        UR.save(usu);
        return "Usuario creado correctamente";
    }

    // Obtener todos los usuarios
    public List<Usuario> obtenerUsuarios() {
        return UR.findAll();
    }

    // Buscar por correo
    public Optional<Usuario> buscarPorCorreo(String correo) {
        return UR.findByCorreo(correo);
    }

    // Eliminar usuario
    public boolean eliminarUsuario(Long id) {
        if (UR.existsById(id)) {
            UR.deleteById(id);
            return true;
        }
        return false;
    }

    // Editar usuario
    public Optional<Usuario> editarUsuario(Long id, Usuario datosActualizados) {
        Optional<Usuario> existente = UR.findById(id);
        if (existente.isPresent()) {
            Usuario u = existente.get();
            u.setNombre(datosActualizados.getNombre());
            u.setCorreo(datosActualizados.getCorreo());
            u.setRol(datosActualizados.getRol());
            return Optional.of(UR.save(u));
        }
        return Optional.empty();
    }

    //  Crear usuario admin por defecto al iniciar microservicio
    @PostConstruct
    public void crearAdminPorDefecto() {
        if (!UR.existsByCorreo("admin@admin.cl")) {
            Rol rolAdmin = rolRepository.findById(1L).orElse(null);
            if (rolAdmin != null) {
                Usuario admin = new Usuario();
                admin.setNombre("admin");
                admin.setCorreo("admin@admin.cl");
                admin.setContrasena(PE.encode("admin123"));
                admin.setRol(rolAdmin);
                UR.save(admin);
                System.out.println(" Usuario admin creado automáticamente");
            } else {
                System.out.println(" No se encontró el rol ADMINISTRADOR (ID 1), no se creó el usuario admin");
            }
        }
    }
}
