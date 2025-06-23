package com.example.Resenas.service;

import com.example.Resenas.model.Resenas;
import com.example.Resenas.repository.ResenaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ResenasServiceTest {

    private ResenaRepository repoMock;
    private ResenasService service;

    // Ejemplo de reseña: id=1, tipo="positiva", usuarioId=100
    private final Resenas muestra = new Resenas(1L, "positiva", 100L);

    @BeforeEach
    void setUp() {
        repoMock = mock(ResenaRepository.class);
        service = new ResenasService(repoMock);
    }

    // listarResenas
    @Test
    void listarResenas_devuelveLista() {
        when(repoMock.findAll()).thenReturn(List.of(muestra));

        List<Resenas> resultado = service.listarResenas();

        assertEquals(1, resultado.size());
        assertEquals("positiva", resultado.get(0).getTipoResena());
    }

    // agregarResenas
    @Test
    void agregarResenas_guardaYRetorna() {
        when(repoMock.save(muestra)).thenReturn(muestra);

        Resenas guardada = service.agregarResena(muestra);

        assertNotNull(guardada);
        assertEquals("positiva", guardada.getTipoResena());
        assertEquals(100L, guardada.getUsuarioId());
        verify(repoMock, times(1)).save(muestra);
    }

    // listarPorUsuario
    @Test
    void listarPorUsuario_devuelveResenas() {
        when(repoMock.findByUsuarioId(100L)).thenReturn(List.of(muestra));

        List<Resenas> lista = service.listarPorUsuario(100L);

        assertFalse(lista.isEmpty());
        assertEquals(100L, lista.get(0).getUsuarioId());
    }

    // buscarPorId
    @Test
    void buscarPorId_devuelveOptional() {
        when(repoMock.findById(1L)).thenReturn(Optional.of(muestra));

        Optional<Resenas> encontrada = service.buscarPorId(1L);

        assertTrue(encontrada.isPresent());
        assertEquals("positiva", encontrada.get().getTipoResena());
    }

    // modificarResena - existente
    @Test
    void modificarResena_existente_retornaOptionalConDato() {
        Resenas actualizada = new Resenas(1L, "negativa", 100L);

        when(repoMock.existsById(1L)).thenReturn(true);
        when(repoMock.findById(1L)).thenReturn(Optional.of(muestra));
        when(repoMock.save(any(Resenas.class))).thenReturn(actualizada);

        Optional<Resenas> resultado = service.modificarResena(1L, actualizada);

        assertTrue(resultado.isPresent());
        assertEquals("negativa", resultado.get().getTipoResena());
        verify(repoMock, times(1)).save(any(Resenas.class));
    }

    // modificarResena - no existe
    @Test
    void modificarResena_noExiste_retornaEmpty() {
        Resenas actualizada = new Resenas(99L, "neutra", 100L);

        when(repoMock.existsById(99L)).thenReturn(false);

        Optional<Resenas> resultado = service.modificarResena(99L, actualizada);

        assertTrue(resultado.isEmpty());
        verify(repoMock, never()).save(any());
    }

    // eliminarPorId - existe
    @Test
    void eliminarPorId_existente_retornaTrue() {
        when(repoMock.existsById(1L)).thenReturn(true);

        boolean resultado = service.eliminarPorId(1L);

        assertTrue(resultado);
        verify(repoMock, times(1)).deleteById(1L);
    }

    // eliminarPorId - no existe
    @Test
    void eliminarPorId_inexistente_retornaFalse() {
        when(repoMock.existsById(99L)).thenReturn(false);

        boolean resultado = service.eliminarPorId(99L);

        assertFalse(resultado);
        verify(repoMock, never()).deleteById(anyLong());
    }
}