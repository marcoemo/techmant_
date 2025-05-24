package com.example.Autenticacion.service;

import com.example.Autenticacion.webclient.UsuarioClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.Map;

@Service
public class AuthService {

    @Autowired
    private UsuarioClient usuarioClient;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String autenticarUsuario(String correo, String contrasena) {
        try {
            Map<String, Object> usuario = usuarioClient.obtenerUsuarioPorCorreo(correo);

            if (usuario == null || usuario.get("contrasena") == null) {
                return "Usuario no encontrado";
            }

            String contrasenaEncriptada = usuario.get("contrasena").toString();
            boolean coincide = passwordEncoder.matches(contrasena, contrasenaEncriptada);

            return coincide ? "Datos Coinciden" : "Contraseña no válida";

        } catch (Exception e) {
            return "Error al autenticar: " + e.getMessage();
        }
    }
}