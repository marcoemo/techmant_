package com.example.Seguimiento.service;

import com.example.Seguimiento.model.Seguimiento;
import com.example.Seguimiento.repository.SeguimientoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SeguimientoServiceTest {

    @Mock
    private SeguimientoRepository repo;

    @InjectMocks
    private SeguimientoService service;

    private Seguimiento seguimiento;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        seguimiento = new Seguimiento(1L, "En diagnóstico", "Observaciones X", 10L);
    }

    @Test
    void obtenerTodos_devuelveLista() {
        List<Seguimiento> lista = List.of(seguimiento);
        when(repo.findAll()).thenReturn(lista);

        List<Seguimiento> resultado = service.obtenerTodos();
        assertEquals(1, resultado.size());
        assertEquals("En diagnóstico", resultado.get(0).getEstado());
    }

    @Test
    void obtenerPorId_devuelveSeguimiento() {
        when(repo.findById(1L)).thenReturn(Optional.of(seguimiento));

        Optional<Seguimiento> resultado = service.obtenerPorId(1L);
        assertTrue(resultado.isPresent());
        assertEquals("Observaciones X", resultado.get().getObservaciones());
    }

    @Test
    void crear_guardaYDevuelveSeguimiento() {
        when(repo.save(seguimiento)).thenReturn(seguimiento);

        Seguimiento resultado = service.crear(seguimiento);
        assertNotNull(resultado);
        assertEquals("En diagnóstico", resultado.getEstado());
    }

    @Test
    void actualizar_actualizaYDevuelveSeguimiento() {
        Seguimiento nuevo = new Seguimiento(null, "Reparado", "Todo bien", 20L);
        when(repo.findById(1L)).thenReturn(Optional.of(seguimiento));
        when(repo.save(any(Seguimiento.class))).thenReturn(seguimiento);

        Optional<Seguimiento> resultado = service.actualizar(1L, nuevo);

        assertTrue(resultado.isPresent());
        verify(repo).save(seguimiento);
    }

    @Test
    void eliminar_eliminaCorrectamente() {
        doNothing().when(repo).deleteById(1L);

        service.eliminar(1L);

        verify(repo, times(1)).deleteById(1L);
    }

    @Test
    void filtrarPorSolicitud_devuelveListaFiltrada() {
        when(repo.findBySolicitudId(10L)).thenReturn(List.of(seguimiento));

        List<Seguimiento> resultado = service.filtrarPorSolicitud(10L);
        assertEquals(1, resultado.size());
        assertEquals(10L, resultado.get(0).getSolicitudId());
    }

    @Test
    void cargarSeguimientosIniciales_agregaSeguimientosSiNoExisten() {
        // Simula que no existen seguimientos con esos solicitudId
        when(repo.findAll()).thenReturn(List.of());

        // Simula el guardado de seguimientos
        when(repo.save(any(Seguimiento.class))).thenAnswer(invocation -> invocation.getArgument(0));

        service.cargarSeguimientosIniciales();

        // Debe intentar guardar 3 seguimientos
        verify(repo, times(3)).save(any(Seguimiento.class));
    }

    @Test
    void cargarSeguimientosIniciales_noAgregaSiYaExisten() {
        // Simula que ya existen seguimientos con esos solicitudId
        Seguimiento s1 = new Seguimiento(1L, "En revisión", "Se está analizando el problema", 1L);
        Seguimiento s2 = new Seguimiento(2L, "Esperando repuestos", "Se enviará a taller", 2L);
        Seguimiento s3 = new Seguimiento(3L, "Finalizado", "Equipo reparado y entregado", 3L);
        when(repo.findAll()).thenReturn(List.of(s1, s2, s3));

        service.cargarSeguimientosIniciales();

        // No debe intentar guardar nada porque ya existen
        verify(repo, never()).save(any(Seguimiento.class));
    }
} 
