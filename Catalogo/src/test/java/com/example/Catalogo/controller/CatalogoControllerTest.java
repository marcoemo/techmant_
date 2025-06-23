package com.example.Catalogo.controller;

import com.example.Catalogo.model.Catalogo;
import com.example.Catalogo.repository.CatalogoRepository;
import com.example.Catalogo.service.CatalogoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CatalogoController.class)
public class CatalogoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CatalogoRepository catalogoRepository;

    // Listar todos los catálogos
    @Test
    void obtenerTodos_devuelveListaDeCatalogos() throws Exception {
        List<Catalogo> catalogos = List.of(
            new Catalogo(1L, "Reparación", "Reparación de hardware y software.", 20000),
            new Catalogo(2L, "Instalación", "Instalación de software.", 10000)
        );
        when(catalogoRepository.findAll()).thenReturn(catalogos);

        mockMvc.perform(get("/catalogos"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].idCatalogo").value(1))
            .andExpect(jsonPath("$[0].nombre").value("Reparación"))
            .andExpect(jsonPath("$[1].idCatalogo").value(2))
            .andExpect(jsonPath("$[1].nombre").value("Instalación"));
    }

    // Obtener catálogo por ID existente
    @Test
    void obtenerPorId_catalogoExistente_devuelveCatalogo() throws Exception {
        Catalogo catalogo = new Catalogo(1L, "Reparación", "Reparación de hardware y software.", 20000);
        when(catalogoRepository.findById(1L)).thenReturn(Optional.of(catalogo));

        mockMvc.perform(get("/catalogos/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.idCatalogo").value(1))
            .andExpect(jsonPath("$.nombre").value("Reparación"));
    }

    // Obtener catálogo por ID no existente
    @Test
    void obtenerPorId_catalogoNoExistente_devuelveNotFound() throws Exception {
        when(catalogoRepository.findById(999L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/catalogos/999"))
            .andExpect(status().isNotFound());
    }

    // Crear nuevo catálogo
    @Test
    void crearCatalogo_devuelveCreatedConCatalogo() throws Exception {
        Catalogo nuevo = new Catalogo(3L, "Mantenimiento", "Mantenimiento preventivo.", 15000);
        when(catalogoRepository.save(org.mockito.ArgumentMatchers.any(Catalogo.class))).thenReturn(nuevo);

        String jsonNuevo = """
            {
                "idCatalogo": 3,
                "nombre": "Mantenimiento",
                "descripcion": "Mantenimiento preventivo.",
                "precio": 15000
            }
        """;

        mockMvc.perform(post("/catalogos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonNuevo))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.idCatalogo").value(3))
            .andExpect(jsonPath("$.nombre").value("Mantenimiento"));
    }

    // Actualizar catálogo existente
    @Test
    void actualizarCatalogo_catalogoExistente_devuelveCatalogoActualizado() throws Exception {
        Catalogo existente = new Catalogo(1L, "Reparación", "Reparación de hardware y software.", 20000);
        Catalogo actualizado = new Catalogo(1L, "Reparación Mejorada", "Reparación avanzada.", 25000);

        when(catalogoRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(catalogoRepository.save(org.mockito.ArgumentMatchers.any(Catalogo.class))).thenReturn(actualizado);

        String jsonActualizado = """
            {
                "idCatalogo": 1,
                "nombre": "Reparación Mejorada",
                "descripcion": "Reparación avanzada.",
                "precio": 25000
            }
        """;

        mockMvc.perform(put("/catalogos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonActualizado))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.nombre").value("Reparación Mejorada"))
            .andExpect(jsonPath("$.precio").value(25000));
    }

    // Actualizar catálogo no existente
    @Test
    void actualizarCatalogo_catalogoNoExistente_devuelveNotFound() throws Exception {
        when(catalogoRepository.findById(999L)).thenReturn(Optional.empty());

        String json = """
            {
                "idCatalogo": 999,
                "nombre": "Inexistente",
                "descripcion": "No existe.",
                "precio": 0
            }
        """;

        mockMvc.perform(put("/catalogos/999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
            .andExpect(status().isNotFound());
    }

    // Eliminar catálogo existente
    @Test
    void eliminarCatalogo_catalogoExistente_devuelveNoContent() throws Exception {
        when(catalogoRepository.existsById(1L)).thenReturn(true);

        mockMvc.perform(delete("/catalogos/1"))
            .andExpect(status().isNoContent());
    }

    // Eliminar catálogo no existente
    @Test
    void eliminarCatalogo_catalogoNoExistente_devuelveNotFound() throws Exception {
        when(catalogoRepository.existsById(999L)).thenReturn(false);

        mockMvc.perform(delete("/catalogos/999"))
            .andExpect(status().isNotFound());
    }
}
