package com.example.GestionDeUsuarios.service;

import com.example.GestionDeUsuarios.model.Rol;
import com.example.GestionDeUsuarios.model.Usuario;
import com.example.GestionDeUsuarios.repository.RolRepository;
import com.example.GestionDeUsuarios.repository.UsuarioRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuarioServiceTest {

    private UsuarioRepository usuarioRepository;
    private RolRepository rolRepository;
    private PasswordEncoder passwordEncoder;
    private UsuarioService usuarioService;

    @BeforeEach
    void setUp() {
        usuarioRepository = mock(UsuarioRepository.class);
        rolRepository = mock(RolRepository.class);
        passwordEncoder = mock(PasswordEncoder.class);
        usuarioService = new UsuarioService(usuarioRepository, passwordEncoder);

        // Inyección segura del repositorio privado
        ReflectionTestUtils.setField(usuarioService, "rolRepository", rolRepository);
    }

    @Test
    void guardarUsuario_correoNuevo_creaUsuario() {
        Usuario nuevo = new Usuario(null, "Pedro", "pedro@mail.com", "abc", new Rol(5L, "USUARIO"));
        when(usuarioRepository.existsByCorreo("pedro@mail.com")).thenReturn(false);
        when(passwordEncoder.encode("abc")).thenReturn("encrypted");
        when(usuarioRepository.save(any())).thenReturn(nuevo);

        String resultado = usuarioService.guardarUsuario(nuevo);
        assertEquals("Usuario creado correctamente", resultado);
        verify(usuarioRepository).save(any(Usuario.class));
    }

    @Test
    void guardarUsuario_correoYaExiste_retornaError() {
        when(usuarioRepository.existsByCorreo("existe@mail.com")).thenReturn(true);

        Usuario repetido = new Usuario(null, "Ana", "existe@mail.com", "123", null);
        String resultado = usuarioService.guardarUsuario(repetido);

        assertEquals("Ya existe un usuario con ese correo", resultado);
        verify(usuarioRepository, never()).save(any());
    }

    @Test
    void eliminarUsuario_existe_elimina() {
        when(usuarioRepository.existsById(1L)).thenReturn(true);

        boolean eliminado = usuarioService.eliminarUsuario(1L);
        assertTrue(eliminado);
        verify(usuarioRepository).deleteById(1L);
    }

    @Test
    void eliminarUsuario_noExiste_falla() {
        when(usuarioRepository.existsById(999L)).thenReturn(false);

        boolean eliminado = usuarioService.eliminarUsuario(999L);
        assertFalse(eliminado);
    }

    @Test
    void editarUsuario_existente_actualiza() {
        Usuario existente = new Usuario(1L, "Pedro", "pedro@mail.com", "123", new Rol(5L, "USUARIO"));
        Usuario actualizado = new Usuario(null, "Pedro Editado", "editado@mail.com", "123", new Rol(1L, "ADMIN"));

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(usuarioRepository.save(any())).thenReturn(existente);

        Optional<Usuario> resultado = usuarioService.editarUsuario(1L, actualizado);

        assertTrue(resultado.isPresent());
        assertEquals("editado@mail.com", resultado.get().getCorreo());
        assertEquals("Pedro Editado", resultado.get().getNombre());
    }
}
