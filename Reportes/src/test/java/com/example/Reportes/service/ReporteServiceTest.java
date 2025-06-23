package com.example.Reportes.service;

import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;

import com.example.Reportes.model.Reporte;
import com.example.Reportes.repository.ReporteRepository;

@ExtendWith(MockitoExtension.class)
public class ReporteServiceTest {
    @Mock
    private ReporteRepository repository;

    @InjectMocks
    private ReporteService service;

    @Test
    void obtenerPorId_returnsDiagnosticoIfExists() {
        Reporte d = new Reporte(1L, LocalDate.now(), 10000, 1L);
        when(repository.findById(1L)).thenReturn(java.util.Optional.of(d));

        Reporte result = service.obtenerPorId(1L);

        assertThat(result).isEqualTo(d);
    }

    @Test
    void obtenerPorId_returnsNullIfNotExists() {
        when(repository.findById(2L)).thenReturn(java.util.Optional.empty());

        Reporte result = service.obtenerPorId(2L);

        assertThat(result).isNull();
    }

    @Test
    void obtenerTodos_returnsListOfReportes() {
        Reporte r1 = new Reporte(1L, LocalDate.now(), 10000, 1L);
        Reporte r2 = new Reporte(2L, LocalDate.now(), 20000, 2L);
        when(repository.findAll()).thenReturn(java.util.Arrays.asList(r1, r2));

        var result = service.obtenerTodos();

        assertThat(result).containsExactly(r1, r2);
    }

    @Test
    void agregarReporte_savesAndReturnsReporte() {
        LocalDate fecha = LocalDate.now();
        int costo = 5000;
        Long diagId = 3L;
        Reporte saved = new Reporte(null, fecha, costo, diagId);
        when(repository.save(org.mockito.ArgumentMatchers.any(Reporte.class))).thenReturn(saved);

        Reporte result = service.agregarReporte(fecha, costo, diagId);

        assertThat(result).isEqualTo(saved);
    }

    @Test
    void eliminarPorId_deletesIfExists() {
        Long id = 1L;
        when(repository.existsById(id)).thenReturn(true);

        service.eliminarPorId(id);

        org.mockito.Mockito.verify(repository).deleteById(id);
    }

    @Test
    void eliminarPorId_doesNothingIfNotExists() {
        Long id = 2L;
        when(repository.existsById(id)).thenReturn(false);

        service.eliminarPorId(id);

        org.mockito.Mockito.verify(repository, org.mockito.Mockito.never()).deleteById(id);
    }
}
