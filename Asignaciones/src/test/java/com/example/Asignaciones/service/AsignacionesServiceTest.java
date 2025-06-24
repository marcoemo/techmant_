package com.example.Asignaciones.service;

import com.example.Asignaciones.model.Tecnico;
import com.example.Asignaciones.repository.AsignacionRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AsignacionesServiceTest {

    @Mock
    private AsignacionRepository asignacionRepository;

    @InjectMocks
    private AsignacionService asignacionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void obtenerTodos_deberiaRetornarListaDeTecnicos() {
        List<Tecnico> tecnicosMock = Arrays.asList(
            new Tecnico(1L, 1L, "Disponible", 1L),
            new Tecnico(2L, 2L, "Ocupado", 2L)
        );
        when(asignacionRepository.findAll()).thenReturn(tecnicosMock);

        List<Tecnico> resultado = asignacionService.obtenerTodos();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals(tecnicosMock, resultado);
        verify(asignacionRepository).findAll();
    }

    @Test
    void obtenerTodos_deberiaRetornarListaVaciaSiNoHayTecnicos() {
        when(asignacionRepository.findAll()).thenReturn(Collections.emptyList());

        List<Tecnico> resultado = asignacionService.obtenerTodos();

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(asignacionRepository).findAll();
    }

    @Test
    void buscarPorId_deberiaRetornarTecnicoSiExiste() {
        Tecnico tecnicoMock = new Tecnico(10L, 5L, "Disponible", 2L);
        when(asignacionRepository.findById(10L)).thenReturn(Optional.of(tecnicoMock));

        Tecnico resultado = asignacionService.buscarPorId(10L);

        assertNotNull(resultado);
        assertEquals(tecnicoMock, resultado);
        verify(asignacionRepository).findById(10L);
    }

    @Test
    void buscarPorId_deberiaRetornarNullSiNoExiste() {
        when(asignacionRepository.findById(99L)).thenReturn(Optional.empty());

        Tecnico resultado = asignacionService.buscarPorId(99L);

        assertNull(resultado);
        verify(asignacionRepository).findById(99L);
    }


    @Test
    void eliminarPorId_deberiaEliminarSiExiste() {
        Long id = 4L;
        when(asignacionRepository.existsById(id)).thenReturn(true);

        asignacionService.eliminarPorId(id);

        verify(asignacionRepository).deleteById(id);
    }

    @Test
    void eliminarPorId_noDeberiaEliminarSiNoExiste() {
        Long id = 5L;
        when(asignacionRepository.existsById(id)).thenReturn(false);

        asignacionService.eliminarPorId(id);

        verify(asignacionRepository, never()).deleteById(id);
    }
}
