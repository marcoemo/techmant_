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
import static org.mockito.ArgumentMatchers.any;
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
    void obtenerTodos_retornaListaTecnicos() {
        List<Tecnico> tecnicos = List.of(
            new Tecnico(1L,1L,"Disponible",1L),
            new Tecnico(2L, 2L, "Ocupado",1L)
        );

        when(asignacionRepository.findAll()).thenReturn(tecnicos);

        List<Tecnico> resultado = asignacionService.obtenerTodos();

        assertEquals(2, resultado.size());
        verify(asignacionRepository, times(1)).findAll();
    }

    @Test
    void obtenerTodos_retornaListaVaciaCuandoNoHayTecnicos() {
        when(asignacionRepository.findAll()).thenReturn(Collections.emptyList());

        List<Tecnico> resultado = asignacionService.obtenerTodos();

        assertTrue(resultado.isEmpty());
        verify(asignacionRepository, times(1)).findAll();
    }

    @Test
    void obtenerPorId_retornaTecnicoCuandoExiste() {
        Tecnico tecnico = new Tecnico(1L, 1L, "Disponible", 1L);
        when(asignacionRepository.findById(1L)).thenReturn(Optional.of(tecnico));

        Tecnico resultado = asignacionService.buscarPorId(1L);

        assertNotNull(resultado);
        assertEquals(tecnico, resultado);
        verify(asignacionRepository, times(1)).findById(1L);
    }

    @Test
    void obtenerPorId_retornaVacioCuandoNoExiste() {
        when(asignacionRepository.findById(1L)).thenReturn(Optional.empty());

        Tecnico resultado = asignacionService.buscarPorId(1L);

        assertNull(resultado);
        verify(asignacionRepository, times(1)).findById(1L);
    }

    @Test
    void guardarTecnico_guardaYRetornaTecnico() {
        Tecnico tecnico = new Tecnico(1L, 1L, "Disponible", 1L);
        when(asignacionRepository.save(any(Tecnico.class))).thenReturn(tecnico);

        Tecnico resultado = asignacionService.agregarTecnico("Nombre", "Estado", 1L, 1L);

        assertEquals(tecnico, resultado);
        verify(asignacionRepository, times(1)).save(any(Tecnico.class));
    }

    @Test
    void eliminarTecnico_eliminaPorId() {
    Long id = 1L;
    when(asignacionRepository.existsById(id)).thenReturn(true);

    asignacionService.eliminarPorId(id);

    verify(asignacionRepository, times(1)).deleteById(id);
}
}
    
