package com.example.Asignaciones.service;

import com.example.Asignaciones.model.Tecnico;
import com.example.Asignaciones.repository.AsignacionRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AsignacionServiceTest {

    @Mock
    private AsignacionRepository asignacionRepository;

    @InjectMocks
    private AsignacionService asignacionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void obtenerTodos_retornaListaTecnicos() {
        List<Tecnico> tecnicos = List.of(
            new Tecnico(1L, "Ricardo", "Disponible"),
            new Tecnico(2L, "Dariel", "Ocupado")
        );

        when(asignacionRepository.findAll()).thenReturn(tecnicos);

        List<Tecnico> resultado = asignacionService.obtenerTodos();

        assertEquals(2, resultado.size());
        verify(asignacionRepository, times(1)).findAll();
    }

    @Test
    void buscarPorId_tecnicoExiste_retornaTecnico() {
        Tecnico tecnico = new Tecnico(1L, "Ricardo", "Disponible");
        when(asignacionRepository.findById(1L)).thenReturn(Optional.of(tecnico));

        Tecnico resultado = asignacionService.buscarPorId(1L);

        assertNotNull(resultado);
        assertEquals("Ricardo", resultado.getNombreTecnico());
        verify(asignacionRepository).findById(1L);
    }

    @Test
    void buscarPorId_tecnicoNoExiste_retornaNull() {
        when(asignacionRepository.findById(999L)).thenReturn(Optional.empty());

        Tecnico resultado = asignacionService.buscarPorId(999L);

        assertNull(resultado);
        verify(asignacionRepository).findById(999L);
    }

    @Test
    void eliminarPorId_tecnicoExiste_elimina() {
        when(asignacionRepository.existsById(1L)).thenReturn(true);
        doNothing().when(asignacionRepository).deleteById(1L);

        asignacionService.eliminarPorId(1L);

        verify(asignacionRepository).existsById(1L);
        verify(asignacionRepository).deleteById(1L);
    }

    @Test
    void eliminarPorId_tecnicoNoExiste_noHaceNada() {
        when(asignacionRepository.existsById(999L)).thenReturn(false);

        asignacionService.eliminarPorId(999L);

        verify(asignacionRepository).existsById(999L);
        verify(asignacionRepository, never()).deleteById(anyLong());
    }

    @Test
    void modificarDisponibilidad_tecnicoExiste_actualizaYGuarda() {
        Tecnico tecnico = new Tecnico(1L, "Ricardo", "Ocupado");
        when(asignacionRepository.findById(1L)).thenReturn(Optional.of(tecnico));
        when(asignacionRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        asignacionService.modificarDisponibilidad(1L, "Disponible");

        assertEquals("Disponible", tecnico.getDisponibilidad());
        verify(asignacionRepository).findById(1L);
        verify(asignacionRepository).save(tecnico);
    }

    @Test
    void modificarDisponibilidad_tecnicoNoExiste_noHaceNada() {
        when(asignacionRepository.findById(999L)).thenReturn(Optional.empty());

        asignacionService.modificarDisponibilidad(999L, "Disponible");

        verify(asignacionRepository).findById(999L);
        verify(asignacionRepository, never()).save(any());
    }

    @Test
    void agregarTecnico_guardaNuevoTecnico() {
        Tecnico tecnicoEsperado = new Tecnico(4L, "Pablo", "Ocupado");
        when(asignacionRepository.save(any(Tecnico.class))).thenReturn(tecnicoEsperado);

        Tecnico resultado = asignacionService.agregarTecnico("Pablo", "Ocupado");

        assertNotNull(resultado);
        assertEquals("Pablo", resultado.getNombreTecnico());
        assertEquals("Ocupado", resultado.getDisponibilidad());
        verify(asignacionRepository).save(any(Tecnico.class));
    }
}
