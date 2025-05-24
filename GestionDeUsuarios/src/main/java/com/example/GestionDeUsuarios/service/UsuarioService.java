package com.example.GestionDeUsuarios.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.GestionDeUsuarios.model.Usuario;
import com.example.GestionDeUsuarios.repository.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private final UsuarioRepository UR;

    private final PasswordEncoder PE;

    public UsuarioService(UsuarioRepository UR, PasswordEncoder PE) {
        this.UR = UR;
        this.PE = PE;
    }

    // Método para guardar un nuevo usuario con su contraseña encriptada
    public Usuario guardarUsuario(Usuario usu) {
        usu.setContrasena(PE.encode(usu.getContrasena()));
        return UR.save(usu);
    }

    // Obtener todos los usuarios
    public List<Usuario> obtenerUsuarios() {
        return UR.findAll();
    }

    // Buscar usuario por correo (para que autenticación lo consulte)
    public Usuario obtenerPorCorreo(String correo) {
        return UR.findByCorreo(correo).orElse(null);
    }
}