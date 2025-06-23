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

    // Crear usuario con validación de correo duplicado -- Todos los usuarios
    public String guardarUsuario(Usuario usu) {
        if (UR.existsByCorreo(usu.getCorreo())) {
            return "Ya existe un usuario con ese correo";
        }
        usu.setContrasena(PE.encode(usu.getContrasena()));
        UR.save(usu);
        return "Usuario creado correctamente";
    }

    // Obtener todos los usuarios -- Admin
    public List<Usuario> obtenerUsuarios() {
        return UR.findAll();
    }

    // Buscar por correo -- Admin
    public Optional<Usuario> buscarPorCorreo(String correo) {
        return UR.findByCorreo(correo);
    }

    // Eliminar usuario -- todos los usuarios
    public boolean eliminarUsuario(Long id) {
        if (UR.existsById(id)) {
            UR.deleteById(id);
            return true;
        }
        return false;
    }

    // Editar usuario -- Todos los usuarios
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

    @PostConstruct
    public void crearUsuariosPorDefecto() {
    // Crear técnicos
    crearUsuariosParaRol(2L, "TÉCNICO", List.of(
        new UsuarioDefecto("Técnico Pedro", "tecnicoPedro@techmant.cl", "tecnicoP123"),
        new UsuarioDefecto("Técnica María", "tecnicaMaria@techmant.cl", "tecnicoM456"),
        new UsuarioDefecto("Técnico Carlos", "tecnicoCarlos@techmant.cl", "tecnicoC789")
    ));

    // Crear clientes
    crearUsuariosParaRol(5L, "USUARIO", List.of(
        new UsuarioDefecto("Cliente Ana", "clienteAna@gmail.cl", "clienteA123"),
        new UsuarioDefecto("Cliente Luis", "clienteLuis@gmail.cl", "clienteL456"),
        new UsuarioDefecto("Cliente Sofia", "clienteSofia@gmail.cl","clienteS789")
    ));

    // Crear soporte
    crearUsuariosParaRol(4L, "SOPORTE", List.of(
        new UsuarioDefecto("Soporte Juan", "soporteJuan@techmant.cl", "soporteJ123"),
        new UsuarioDefecto("Soporte Laura", "soportelaura@techmant","soporteL456"),
        new UsuarioDefecto("Soporte Diego", "soportediego@techmant.cl", "soporteD456")
    ));

    // Crear supervisor
    crearUsuariosParaRol(3L, "SUPERVISOR", List.of(
        new UsuarioDefecto("Supervisor Ana", "supervisorAna@techamnt.cl","supervisorA123"),
        new UsuarioDefecto("Supervisor Luis", "supervisorLuis@techamnt.cl","supervisorL456"),
        new UsuarioDefecto("Supervisor Sofia", "supervisorSofia@techamnt.cl","supervisorS789")
    ));

    // Crear administrador
    crearUsuariosParaRol(1L, "ADMINISTRADOR", List.of(
        new UsuarioDefecto("Admin1", "admin1@admin.cl", "admin123")
    ));
    }

    // Clase auxiliar para datos de usuario
    private record UsuarioDefecto(String nombre, String correo, String contrasena) {}

    // Método genérico para crear usuarios
    private void crearUsuariosParaRol(Long rolId, String nombreRol, List<UsuarioDefecto> usuarios) {
    Rol rol = rolRepository.findById(rolId).orElse(null);

    for (UsuarioDefecto datos : usuarios) {
        if (!UR.existsByCorreo(datos.correo())) {
            Usuario usuario = new Usuario();
            usuario.setNombre(datos.nombre());
            usuario.setCorreo(datos.correo());
            usuario.setContrasena(PE.encode(datos.contrasena()));
            usuario.setRol(rol);  // Asigna el rol
            
            UR.save(usuario);  // ID se genera automáticamente aquí
            }
        }
    }
}