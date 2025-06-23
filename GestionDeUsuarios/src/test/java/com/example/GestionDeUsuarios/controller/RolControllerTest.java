package com.example.GestionDeUsuarios.controller;

import com.example.GestionDeUsuarios.model.Rol;
import com.example.GestionDeUsuarios.service.RolService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RolController.class)
@AutoConfigureMockMvc(addFilters = false)
public class RolControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RolService rolService;

    @Test
    void obtenerTodos_devuelveListaDeRoles() throws Exception {
        List<Rol> roles = List.of(
            new Rol(1L, "ADMINISTRADOR"),
            new Rol(2L, "USUARIO")
        );

        when(rolService.obtenerTodos()).thenReturn(roles);

        mockMvc.perform(get("/roles"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].idRol").value(1L))
            .andExpect(jsonPath("$[0].nombreRol").value("ADMINISTRADOR"))
            .andExpect(jsonPath("$[1].idRol").value(2L))
            .andExpect(jsonPath("$[1].nombreRol").value("USUARIO"));
    }

    @Test
    void obtenerPorId_existente_devuelveRol() throws Exception {
        Rol rol = new Rol(1L, "ADMINISTRADOR");
        when(rolService.obtenerPorId(1L)).thenReturn(Optional.of(rol));

        mockMvc.perform(get("/roles/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.idRol").value(1L))
            .andExpect(jsonPath("$.nombreRol").value("ADMINISTRADOR"));
    }

    @Test
    void obtenerPorId_noExistente_devuelve404() throws Exception {
        when(rolService.obtenerPorId(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/roles/99"))
            .andExpect(status().isNotFound());
    }

    @Test
    void crearRol_retorna201() throws Exception {
        Rol nuevo = new Rol(10L, "NUEVO_ROL");
        when(rolService.guardarRol(any(Rol.class))).thenReturn(nuevo);

        mockMvc.perform(post("/roles")
                .contentType("application/json")
                .content("""
                    {
                        "idRol": 10,
                        "nombreRol": "NUEVO_ROL"
                    }
                """))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.idRol").value(10))
            .andExpect(jsonPath("$.nombreRol").value("NUEVO_ROL"));
    }

    @Test
    void actualizarRol_existente_retorna200() throws Exception {
        Rol actualizado = new Rol(1L, "ROL_ACTUALIZADO");
        when(rolService.actualizarRol(eq(1L), any(Rol.class)))
            .thenReturn(Optional.of(actualizado));

        mockMvc.perform(put("/roles/1")
                .contentType("application/json")
                .content("""
                    {
                        "idRol": 1,
                        "nombreRol": "ROL_ACTUALIZADO"
                    }
                """))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.idRol").value(1))
            .andExpect(jsonPath("$.nombreRol").value("ROL_ACTUALIZADO"));
    }

    @Test
    void actualizarRol_noExistente_retorna404() throws Exception {
        when(rolService.actualizarRol(eq(99L), any(Rol.class)))
            .thenReturn(Optional.empty());

        mockMvc.perform(put("/roles/99")
                .contentType("application/json")
                .content("""
                    {
                        "idRol": 99,
                        "nombreRol": "INEXISTENTE"
                    }
                """))
            .andExpect(status().isNotFound());
    }

    @Test
    void eliminarRol_existente_retorna204() throws Exception {
        when(rolService.eliminarRol(1L)).thenReturn(true);

        mockMvc.perform(delete("/roles/1"))
            .andExpect(status().isNoContent());
    }

    @Test
    void eliminarRol_noExistente_retorna404() throws Exception {
        when(rolService.eliminarRol(99L)).thenReturn(false);

        mockMvc.perform(delete("/roles/99"))
            .andExpect(status().isNotFound());
    }
}
