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

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RolController.class)
@AutoConfigureMockMvc(addFilters = false) // <--- desactiva filtros de seguridad
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
}
