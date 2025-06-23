package com.example.GestionDeUsuarios.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.Optional;

import com.example.GestionDeUsuarios.model.Rol;
import com.example.GestionDeUsuarios.model.Usuario;
import com.example.GestionDeUsuarios.service.UsuarioService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(UsuarioController.class)
@AutoConfigureMockMvc(addFilters = false) // <--- desactiva filtros de seguridad
public class UsuarioControllerTest {

    @MockBean
    private UsuarioService US;

    @Autowired
    private MockMvc mockMvc;

    private final Rol rolUsuario = new Rol(5L, "USUARIO");

    // 🟩 Crear usuario
    @Test
    void crearUsuario_returns201Created() throws Exception {
        when(US.guardarUsuario(org.mockito.ArgumentMatchers.any(Usuario.class)))
            .thenReturn("Usuario creado correctamente");

        mockMvc.perform(post("/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "nombre": "Juan Pérez",
                        "correo": "juan.perez@ejemplo.com",
                        "contrasena": "clave123",
                        "rol": { "idRol": 5, "nombreRol": "USUARIO" }
                    }
                    """))
            .andExpect(status().isCreated())
            .andExpect(content().string("Usuario creado correctamente"));
    }

    @Test
    void crearUsuario_returns409ConflictIfExists() throws Exception {
        when(US.guardarUsuario(org.mockito.ArgumentMatchers.any(Usuario.class)))
            .thenReturn("Ya existe un usuario con ese correo");

        mockMvc.perform(post("/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "nombre": "Juan Pérez",
                        "correo": "juan.perez@ejemplo.com",
                        "contrasena": "clave123",
                        "rol": { "idRol": 5, "nombreRol": "USUARIO" }
                    }
                    """))
            .andExpect(status().isConflict())
            .andExpect(content().string("Ya existe un usuario con ese correo"));
    }

    // 🟦 Listar todos los usuarios
    @Test
    void listaUsuarios_returnsList() throws Exception {
        List<Usuario> lista = List.of(
            new Usuario(1L, "Ana", "ana@mail.com", "123", rolUsuario),
            new Usuario(2L, "Luis", "luis@mail.com", "456", rolUsuario)
        );
        when(US.obtenerUsuarios()).thenReturn(lista);

        mockMvc.perform(get("/usuarios"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].idUsuario").value(1L))
            .andExpect(jsonPath("$[0].correo").value("ana@mail.com"));
    }

    // 🟨 Buscar usuario por correo
    @Test
    void obtenerPorCorreo_returnsUsuario() throws Exception {
        Usuario usu = new Usuario(3L, "Carlos", "carlos@mail.com", "clave", rolUsuario);
        when(US.buscarPorCorreo("carlos@mail.com")).thenReturn(Optional.of(usu));

        mockMvc.perform(get("/usuarios/correo/carlos@mail.com"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.correo").value("carlos@mail.com"));
    }

    @Test
    void obtenerPorCorreo_returnsNotFound() throws Exception {
        when(US.buscarPorCorreo("noexiste@mail.com")).thenReturn(Optional.empty());

        mockMvc.perform(get("/usuarios/correo/noexiste@mail.com"))
            .andExpect(status().isNotFound());
    }

    // 🟥 Eliminar usuario por ID
    @Test
    void eliminarUsuario_returnsNoContent() throws Exception {
        when(US.eliminarUsuario(10L)).thenReturn(true);

        mockMvc.perform(delete("/usuarios/10"))
            .andExpect(status().isNoContent());
    }

    @Test
    void eliminarUsuario_returnsNotFound() throws Exception {
        when(US.eliminarUsuario(99L)).thenReturn(false);

        mockMvc.perform(delete("/usuarios/99"))
            .andExpect(status().isNotFound());
    }

    // 🟧 Editar usuario por ID
    @Test
    void editarUsuario_returnsUpdatedUsuario() throws Exception {
        Usuario actualizado = new Usuario(6L, "Nombre Editado", "editado@mail.com", "claveNueva", rolUsuario);
        when(US.editarUsuario(org.mockito.ArgumentMatchers.eq(6L), org.mockito.ArgumentMatchers.any(Usuario.class)))
            .thenReturn(Optional.of(actualizado));

        mockMvc.perform(put("/usuarios/6")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "nombre": "Nombre Editado",
                        "correo": "editado@mail.com",
                        "contrasena": "claveNueva",
                        "rol": { "idRol": 5, "nombreRol": "USUARIO" }
                    }
                    """))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.correo").value("editado@mail.com"));
    }

    @Test
    void editarUsuario_returnsNotFoundIfMissing() throws Exception {
        when(US.editarUsuario(org.mockito.ArgumentMatchers.eq(100L), org.mockito.ArgumentMatchers.any(Usuario.class)))
            .thenReturn(Optional.empty());

        mockMvc.perform(put("/usuarios/100")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "nombre": "Nombre",
                        "correo": "nuevo@mail.com",
                        "contrasena": "clave",
                        "rol": { "idRol": 5, "nombreRol": "USUARIO" }
                    }
                    """))
            .andExpect(status().isNotFound());
    }
}
