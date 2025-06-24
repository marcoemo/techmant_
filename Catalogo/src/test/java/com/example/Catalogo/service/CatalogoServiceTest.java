package com.example.Catalogo.service;

import com.example.Catalogo.model.Catalogo;
import com.example.Catalogo.repository.CatalogoRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
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

    // Tests for eliminarCatalogo
    @Test
    void eliminarCatalogo_existente_retornaTrueYElimina() {
        when(catalogoRepository.existsById(1L)).thenReturn(true);

        boolean resultado = catalogoService.eliminarCatalogo(1L);

        assertTrue(resultado);
        verify(catalogoRepository, times(1)).deleteById(1L);
    }

    @Test
    void eliminarCatalogo_noExistente_retornaFalse() {
        when(catalogoRepository.existsById(2L)).thenReturn(false);

        boolean resultado = catalogoService.eliminarCatalogo(2L);

        assertFalse(resultado);
        verify(catalogoRepository, never()).deleteById(anyLong());
    }

    // Tests for crearCatalogo
    @Test
    void crearCatalogo_guardaYRetornaCatalogo() {
        Catalogo nuevo = new Catalogo(null, "Nuevo", "Descripcion nueva", 12345);
        Catalogo guardado = new Catalogo(10L, "Nuevo", "Descripcion nueva", 12345);

        when(catalogoRepository.save(nuevo)).thenReturn(guardado);

        Catalogo resultado = catalogoService.crearCatalogo(nuevo);

        assertNotNull(resultado);
        assertEquals(10L, resultado.getIdCatalogo());
        assertEquals("Nuevo", resultado.getNombre());
        verify(catalogoRepository, times(1)).save(nuevo);
    }

    // Tests for actualizarCatalogo
    @Test
    void actualizarCatalogo_existente_actualizaYRetornaCatalogo() {
        Catalogo existente = new Catalogo(1L, "Viejo", "Desc vieja", 1000);
        Catalogo nuevo = new Catalogo(1L, "Nuevo", "Desc nueva", 2000);

        when(catalogoRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(catalogoRepository.save(any(Catalogo.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Catalogo resultado = catalogoService.actualizarCatalogo(1L, nuevo);

        assertNotNull(resultado);
        assertEquals("Nuevo", resultado.getNombre());
        assertEquals("Desc nueva", resultado.getDescripcion());
        assertEquals(2000, resultado.getPrecio());
        verify(catalogoRepository, times(1)).save(existente);
    }

    @Test
    void actualizarCatalogo_noExistente_retornaNull() {
        Catalogo nuevo = new Catalogo(1L, "Nuevo", "Desc nueva", 2000);

        when(catalogoRepository.findById(1L)).thenReturn(Optional.empty());

        Catalogo resultado = catalogoService.actualizarCatalogo(1L, nuevo);

        assertNull(resultado);
        verify(catalogoRepository, never()).save(any());
    }

    @Test
    void CargarServiciosInciales_agregaServiciosSiNoExisten() {
        when(catalogoRepository.existsById(anyLong())).thenReturn(false);
        when(catalogoRepository.save(any(Catalogo.class))).thenAnswer(invocation -> invocation.getArgument(0));

        catalogoService.CargarServiciosInciales();

        verify(catalogoRepository, times(9)).save(any(Catalogo.class));
    }

    @Test
    void CargarServiciosInciales_noAgregaSiYaExisten() {
        when(catalogoRepository.existsById(anyLong())).thenReturn(true);

        catalogoService.CargarServiciosInciales();

        verify(catalogoRepository, never()).save(any(Catalogo.class));
    }
}
