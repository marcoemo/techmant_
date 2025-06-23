package com.example.GestionDeUsuarios.service;

import com.example.GestionDeUsuarios.model.Rol;
import com.example.GestionDeUsuarios.model.Usuario;
import com.example.GestionDeUsuarios.repository.UsuarioRepository;
import com.example.GestionDeUsuarios.repository.RolRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository UR;

    @Mock
    private RolRepository rolRepository;

    @Mock
    private PasswordEncoder PE;

    @InjectMocks
    private UsuarioService usuarioService;

    private Usuario usuario;
    private Rol rol;

    @BeforeEach
    void setUp() {
        rol = new Rol(5L, "USUARIO");
        usuario = new Usuario(1L, "Juan", "juan@mail.com", "123", rol);
    }

    @Test
    void guardarUsuario_nuevoUsuario_guardadoExitosamente() {
        when(UR.existsByCorreo(usuario.getCorreo())).thenReturn(false);
        when(PE.encode("123")).thenReturn("encoded123");
        when(UR.save(any(Usuario.class))).thenReturn(usuario);

        String result = usuarioService.guardarUsuario(usuario);

        assertThat(result).isEqualTo("Usuario creado correctamente");
        verify(UR).save(usuario);
    }

    @Test
    void guardarUsuario_correoDuplicado_noSeGuarda() {
        when(UR.existsByCorreo(usuario.getCorreo())).thenReturn(true);

        String result = usuarioService.guardarUsuario(usuario);

        assertThat(result).isEqualTo("Ya existe un usuario con ese correo");
        verify(UR, never()).save(any());
    }

    @Test
    void obtenerUsuarios_devuelveLista() {
        List<Usuario> lista = List.of(usuario);
        when(UR.findAll()).thenReturn(lista);

        List<Usuario> result = usuarioService.obtenerUsuarios();

        assertThat(result).hasSize(1).contains(usuario);
    }

    @Test
    void buscarPorCorreo_existente_devuelveUsuario() {
        when(UR.findByCorreo("juan@mail.com")).thenReturn(Optional.of(usuario));

        Optional<Usuario> result = usuarioService.buscarPorCorreo("juan@mail.com");

        assertThat(result).isPresent().contains(usuario);
    }

    @Test
    void buscarPorCorreo_noExiste_devuelveVacio() {
        when(UR.findByCorreo("no@existe.com")).thenReturn(Optional.empty());

        Optional<Usuario> result = usuarioService.buscarPorCorreo("no@existe.com");

        assertThat(result).isEmpty();
    }

    @Test
    void eliminarUsuario_existente_devuelveTrue() {
        when(UR.existsById(1L)).thenReturn(true);

        boolean eliminado = usuarioService.eliminarUsuario(1L);

        assertThat(eliminado).isTrue();
        verify(UR).deleteById(1L);
    }

    @Test
    void eliminarUsuario_noExiste_devuelveFalse() {
        when(UR.existsById(99L)).thenReturn(false);

        boolean eliminado = usuarioService.eliminarUsuario(99L);

        assertThat(eliminado).isFalse();
        verify(UR, never()).deleteById(any());
    }

    @Test
    void editarUsuario_existente_actualizaDatos() {
        Usuario nuevo = new Usuario(null, "Pedro", "pedro@mail.com", "clave", rol);

        when(UR.findById(1L)).thenReturn(Optional.of(usuario));
        when(UR.save(any())).thenAnswer(i -> i.getArgument(0));

        Optional<Usuario> result = usuarioService.editarUsuario(1L, nuevo);

        assertThat(result).isPresent();
        assertThat(result.get().getNombre()).isEqualTo("Pedro");
        assertThat(result.get().getCorreo()).isEqualTo("pedro@mail.com");
        assertThat(result.get().getRol()).isEqualTo(rol);
    }

    @Test
    void editarUsuario_noExiste_retornaEmpty() {
        when(UR.findById(100L)).thenReturn(Optional.empty());

        Optional<Usuario> result = usuarioService.editarUsuario(100L, usuario);

        assertThat(result).isEmpty();
    }
}
