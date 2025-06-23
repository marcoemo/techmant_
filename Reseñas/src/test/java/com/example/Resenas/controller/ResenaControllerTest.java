package com.example.Resenas.controller;

import com.example.Resenas.model.Resenas;
import com.example.Resenas.service.ResenasService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ResenaController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ResenaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ResenasService resenasService;

    // Ejemplo de reseña para test (id=1, tipo="positiva", usuarioId=10)
    private final Resenas muestra = new Resenas(1L, "positiva", 10L);

    // listarResenas
    @Test
    void listarResenas_devuelveLista() throws Exception {
        when(resenasService.listarResenas()).thenReturn(List.of(muestra));

        mockMvc.perform(get("/resenias"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].tipoResena").value("positiva"))
                .andExpect(jsonPath("$[0].usuarioId").value(10));
    }

    @Test
    void listarResenas_devuelve204SiVacio() throws Exception {
        when(resenasService.listarResenas()).thenReturn(List.of());

        mockMvc.perform(get("/resenias"))
                .andExpect(status().isNoContent());
    }

    // crearResena
    @Test
    void crearResena_valida_retorna201() throws Exception {
        Resenas mockResena = new Resenas(1L, "positiva", 101L);

        when(resenasService.agregarResena(any(Resenas.class))).thenReturn(mockResena);

        mockMvc.perform(post("/resenias")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "tipoResena": "positiva",
                            "usuarioId": 101
                        }
                        """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.tipoResena").value("positiva"))
                .andExpect(jsonPath("$.usuarioId").value(101));
    }

    @Test
    void crearResena_tipoResenaInvalido_retorna400() throws Exception {
        mockMvc.perform(post("/resenias")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "tipoResena": "excelente",
                            "usuarioId": 10
                        }
                        """))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("El tipo de reseña debe ser: positiva, neutra o negativa"));
    }

    @Test
    void crearResena_sinUsuarioId_retorna400() throws Exception {
        mockMvc.perform(post("/resenias")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "tipoResena": "neutra"
                        }
                        """))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Debe especificar el ID del usuario"));
    }

    // obtenerPorUsuario
    @Test
    void obtenerPorUsuario_devuelveLista() throws Exception {
        when(resenasService.listarPorUsuario(10L)).thenReturn(List.of(muestra));

        mockMvc.perform(get("/resenias/usuario/10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].usuarioId").value(10));
    }

    @Test
    void obtenerPorUsuario_sinResenas_retorna204() throws Exception {
        when(resenasService.listarPorUsuario(10L)).thenReturn(List.of());

        mockMvc.perform(get("/resenias/usuario/10"))
                .andExpect(status().isNoContent());
    }

    // obtenerPorId
    @Test
    void obtenerPorId_devuelveResena() throws Exception {
        when(resenasService.buscarPorId(1L)).thenReturn(Optional.of(muestra));

        mockMvc.perform(get("/resenias/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tipoResena").value("positiva"));
    }

    @Test
    void obtenerPorId_noExiste_retorna404() throws Exception {
        when(resenasService.buscarPorId(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/resenias/99"))
                .andExpect(status().isNotFound());
    }

    // eliminarResena
    @Test
    void eliminarResena_existente_retorna204() throws Exception {
        when(resenasService.eliminarPorId(1L)).thenReturn(true);

        mockMvc.perform(delete("/resenias/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void eliminarResena_inexistente_retorna404() throws Exception {
        when(resenasService.eliminarPorId(999L)).thenReturn(false);

        mockMvc.perform(delete("/resenias/999"))
                .andExpect(status().isNotFound());
    }
}