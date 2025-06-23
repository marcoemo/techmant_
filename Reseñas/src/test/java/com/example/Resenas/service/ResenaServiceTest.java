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

    private final Resenas muestra = new Resenas(1L, "Excelente trabajo", 5, 100L);

    @BeforeEach
    void setUp() {
        repoMock = mock(ResenaRepository.class);
        service = new ResenasService(repoMock);
    }

    //  listarResenas
    @Test
    void listarResenas_devuelveLista() {
        when(repoMock.findAll()).thenReturn(List.of(muestra));

        List<Resenas> resultado = service.listarResenas();

        assertEquals(1, resultado.size());
        assertEquals("Excelente trabajo", resultado.get(0).getResena());
    }

    //  agregarResenas
    @Test
    void agregarResenas_guardaYRetorna() {
        when(repoMock.save(muestra)).thenReturn(muestra);

        Resenas guardada = service.agregarResenas(muestra);

        assertNotNull(guardada);
        assertEquals("Excelente trabajo", guardada.getResena());
        verify(repoMock, times(1)).save(muestra);
    }

    //  listarPorUsuario
    @Test
    void listarPorUsuario_devuelveResenas() {
        when(repoMock.findByUsuarioId(100L)).thenReturn(List.of(muestra));

        List<Resenas> lista = service.listarPorUsuario(100L);

        assertFalse(lista.isEmpty());
        assertEquals(100L, lista.get(0).getUsuarioId());
    }

    //  buscarPorId
    @Test
    void buscarPorId_devuelveOptional() {
        when(repoMock.findById(1L)).thenReturn(Optional.of(muestra));

        Optional<Resenas> encontrada = service.buscarPorId(1L);

        assertTrue(encontrada.isPresent());
        assertEquals("Excelente trabajo", encontrada.get().getResena());
    }

    //  eliminarPorId - existe
    @Test
    void eliminarPorId_existente_retornaTrue() {
        when(repoMock.existsById(1L)).thenReturn(true);

        boolean resultado = service.eliminarPorId(1L);

        assertTrue(resultado);
        verify(repoMock, times(1)).deleteById(1L);
    }

    //  eliminarPorId - no existe
    @Test
    void eliminarPorId_inexistente_retornaFalse() {
        when(repoMock.existsById(99L)).thenReturn(false);

        boolean resultado = service.eliminarPorId(99L);

        assertFalse(resultado);
        verify(repoMock, never()).deleteById(anyLong());
    }
}
