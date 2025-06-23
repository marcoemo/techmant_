package com.example.Respaldo.controller;
import com.example.Respaldo.model.Respaldo;
import com.example.Respaldo.service.RespaldoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(RespaldoController.class)
public class RespaldoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RespaldoService respaldoService;

    @Test
    void crearRespaldo_returnsOkAndRespaldo() throws Exception {
        Respaldo respaldo = new Respaldo();
        respaldo.setId(1L); // Ajusta los campos según tu modelo
        respaldo.setNombreArchivo("respaldo_2024.sql");

        when(respaldoService.generarRespaldo()).thenReturn(respaldo);

        mockMvc.perform(post("/respaldos")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1L))
            .andExpect(jsonPath("$.nombreArchivo").value("respaldo_2024.sql"));
    }
}
