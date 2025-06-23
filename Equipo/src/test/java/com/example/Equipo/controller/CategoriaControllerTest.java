package com.example.Equipo.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.Equipo.model.Categoria;
import com.example.Equipo.service.CategoriaService;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;



//cargamos el controlador que vamos a probar
@WebMvcTest(CategoriaController.class)
public class CategoriaControllerTest {

    // Inyectamos un mock de CategoriaService para manipular nuestro contexto
    @MockBean
    private CategoriaService categoriaService;

    //se inyecta un mock proporcionado por spring para simular la llamada a la api
    @Autowired
    private MockMvc mockMvc;

    @Test
    void crearCategoria_returnsCreated() throws Exception {
        Categoria categoria = new Categoria(1L, "Laptops");

        org.mockito.Mockito.when(categoriaService.crear(org.mockito.ArgumentMatchers.any(Categoria.class))).thenReturn(categoria);

        String categoriaJson = "{\"categoriaId\":1,\"nombre\":\"Laptops\"}";

        mockMvc.perform(
                org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post("/categorias")
                        .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                        .content(categoriaJson)
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.categoriaId").value(1L))
        .andExpect(jsonPath("$.nombre").value("Laptops"));
    }
}
