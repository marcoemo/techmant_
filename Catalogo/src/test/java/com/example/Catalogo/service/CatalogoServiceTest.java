package com.example.Catalogo.service;

import com.example.Catalogo.model.Catalogo;
import com.example.Catalogo.repository.CatalogoRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CatalogoServiceTest {

    @Mock
    private CatalogoRepository catalogoRepository;

    private CatalogoService catalogoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        catalogoService = new CatalogoService(catalogoRepository);
    }

    @Test
    void obtenerCatalogo_retornaListaDeCatalogos() {
        List<Catalogo> lista = List.of(
            new Catalogo(1L, "Reparación", "Descripcion 1", 20000),
            new Catalogo(2L, "Instalación", "Descripcion 2", 10000)
        );

        when(catalogoRepository.findAll()).thenReturn(lista);

        List<Catalogo> resultado = catalogoService.obtenerCatalogo();

        assertEquals(2, resultado.size());
        assertEquals("Reparación", resultado.get(0).getNombre());
        verify(catalogoRepository, times(1)).findAll();
    }

    @Test
    void obtenerPorId_retornaCatalogoExistente() {
        Catalogo catalogo = new Catalogo(1L, "Reparación", "Descripcion", 20000);
        when(catalogoRepository.findById(1L)).thenReturn(Optional.of(catalogo));

        Catalogo resultado = catalogoService.obtenerPorId(1L);

        assertNotNull(resultado);
        assertEquals("Reparación", resultado.getNombre());
    }

    @Test
    void obtenerPorId_retornaNullSiNoExiste() {
        when(catalogoRepository.findById(999L)).thenReturn(Optional.empty());

        Catalogo resultado = catalogoService.obtenerPorId(999L);

        assertNull(resultado);
    }
}
