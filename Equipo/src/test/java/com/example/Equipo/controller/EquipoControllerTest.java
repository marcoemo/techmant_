package com.example.Equipo.controller;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.Equipo.model.Categoria;
import com.example.Equipo.model.Equipo;
import com.example.Equipo.service.EquipoService;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


//cargamos el controlador que vamos a probar
@WebMvcTest(EquipoController.class)
public class EquipoControllerTest {

    //inyectamos un mock de ReservaService para manipular nuestro contexto
    @MockBean
    private EquipoService equipoService;

    //se inyecta un mock proporcionado por spring para simular la llamada a la api
    @Autowired
    private MockMvc mockMvc;

    @Test
    void crearEquipo_returnsCreatedEquipo() throws Exception {
        Categoria categoria = new Categoria();
        Equipo equipo = new Equipo(1L, categoria, "Marca1", "Modelo1", "123456789", 1L);

        when(equipoService.crear(org.mockito.ArgumentMatchers.any(Equipo.class))).thenReturn(equipo);

        String equipoJson = "{\"equipoId\":1,\"categoria\":null,\"marca\":\"Marca1\",\"modelo\":\"Modelo1\",\"numeroSerie\":\"123456789\",\"usuarioId\":1}";

        mockMvc.perform(
                org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post("/equipos")
                        .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                        .content(equipoJson)
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.equipoId").value(1L))
        .andExpect(jsonPath("$.marca").value("Marca1"));
    }

    @Test
    void obtenerEquipoPorId_found_returnsEquipo() throws Exception {
        Categoria categoria = new Categoria();
        Equipo equipo = new Equipo(2L, categoria, "Marca2", "Modelo2", "987654321", 2L);

        when(equipoService.obtenerPorId(2L)).thenReturn(equipo);

        mockMvc.perform(get("/equipos/2"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.equipoId").value(2L))
            .andExpect(jsonPath("$.marca").value("Marca2"));
    }

    @Test
    void obtenerEquipoPorId_notFound_returns404() throws Exception {
        when(equipoService.obtenerPorId(99L)).thenReturn(null);

        mockMvc.perform(get("/equipos/99"))
            .andExpect(status().isNotFound());
    }

    @Test
    void buscarPorUsuario_returnsEquipos() throws Exception {
        Categoria categoria = new Categoria();
        List<Equipo> equipos = Arrays.asList(
            new Equipo(3L, categoria, "Marca3", "Modelo3", "111222333", 5L)
        );

        when(equipoService.buscarPorUsuario(5L)).thenReturn(equipos);

        mockMvc.perform(get("/equipos/usuario/5"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].equipoId").value(3L))
            .andExpect(jsonPath("$[0].idUsuario").value(5L));
    }

    @Test
    void actualizarEquipo_found_returnsUpdatedEquipo() throws Exception {
        Categoria categoria = new Categoria();
        Equipo equipo = new Equipo(4L, categoria, "Marca4", "Modelo4", "444555666", 6L);

        when(equipoService.actualizar(org.mockito.ArgumentMatchers.eq(4L), org.mockito.ArgumentMatchers.any(Equipo.class)))
            .thenReturn(equipo);

        String equipoJson = "{\"equipoId\":4,\"categoria\":null,\"marca\":\"Marca4\",\"modelo\":\"Modelo4\",\"numeroSerie\":\"444555666\",\"usuarioId\":6}";

        mockMvc.perform(
                org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put("/equipos/4")
                        .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                        .content(equipoJson)
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.equipoId").value(4L))
        .andExpect(jsonPath("$.marca").value("Marca4"));
    }

    @Test
    void actualizarEquipo_notFound_returns404() throws Exception {
        when(equipoService.actualizar(org.mockito.ArgumentMatchers.eq(10L), org.mockito.ArgumentMatchers.any(Equipo.class)))
            .thenReturn(null);

        String equipoJson = "{\"equipoId\":10,\"categoria\":null,\"marca\":\"MarcaX\",\"modelo\":\"ModeloX\",\"numeroSerie\":\"000000000\",\"usuarioId\":10}";

        mockMvc.perform(
                org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put("/equipos/10")
                        .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                        .content(equipoJson)
        )
        .andExpect(status().isNotFound());
    }

    @Test
    void eliminarEquipo_returnsNoContent() throws Exception {
        org.mockito.Mockito.doNothing().when(equipoService).eliminarPorId(7L);

        mockMvc.perform(
                org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete("/equipos/7")
        )
        .andExpect(status().isNoContent());
    }

}