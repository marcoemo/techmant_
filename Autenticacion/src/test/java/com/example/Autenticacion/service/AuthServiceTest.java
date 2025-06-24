package com.example.Autenticacion.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.Autenticacion.client.UsuarioClient;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class AuthServiceTest {

    @Mock
    private UsuarioClient usuarioClient;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthService authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void autenticarUsuario_retornaDatosCoinciden_siContrasenaCorrecta() {
        Map<String, Object> usuario = new HashMap<>();
        usuario.put("contrasena", "encriptada");

        when(usuarioClient.obtenerUsuarioPorCorreo("lore@gmail.com")).thenReturn(usuario);
        when(passwordEncoder.matches("abc123", "encriptada")).thenReturn(true);

        String resultado = authService.autenticarUsuario("lore@gmail.com", "abc123");

        assertEquals("Datos Coinciden", resultado);
    }

    @Test
    void autenticarUsuario_retornaContrasenaNoValida_siContrasenaIncorrecta() {
        Map<String, Object> usuario = new HashMap<>();
        usuario.put("contrasena", "encriptada");

        when(usuarioClient.obtenerUsuarioPorCorreo("lore@gmail.com")).thenReturn(usuario);
        when(passwordEncoder.matches("abc123", "encriptada")).thenReturn(false);

        String resultado = authService.autenticarUsuario("lore@gmail.com", "abc123");

        assertEquals("Contraseña no válida", resultado);
    }

    @Test
    void autenticarUsuario_retornaUsuarioNoEncontrado_siNoHayUsuario() {
        when(usuarioClient.obtenerUsuarioPorCorreo("desconocido@mail.com")).thenReturn(null);

        String resultado = authService.autenticarUsuario("desconocido@mail.com", "algo");

        assertEquals("Usuario no encontrado", resultado);
    }

    @Test
    void autenticarUsuario_retornaUsuarioNoEncontrado_siNoTieneContrasena() {
        Map<String, Object> usuario = new HashMap<>();
        // no tiene campo "contrasena"

        when(usuarioClient.obtenerUsuarioPorCorreo("sinpass@mail.com")).thenReturn(usuario);

        String resultado = authService.autenticarUsuario("sinpass@mail.com", "algo");

        assertEquals("Usuario no encontrado", resultado);
    }

    @Test
    void autenticarUsuario_retornaError_siExcepcion() {
        when(usuarioClient.obtenerUsuarioPorCorreo("error@mail.com")).thenThrow(new RuntimeException("falló todo"));

        String resultado = authService.autenticarUsuario("error@mail.com", "clave");

        assertEquals("Error al autenticar: falló todo", resultado);
    }
}
