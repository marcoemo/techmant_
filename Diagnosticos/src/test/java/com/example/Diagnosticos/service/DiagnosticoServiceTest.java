package com.example.Diagnosticos.service;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.Diagnosticos.model.Diagnostico;
import com.example.Diagnosticos.repository.DiagnosticoRepository;

@ExtendWith(MockitoExtension.class)
public class DiagnosticoServiceTest {
    @Mock
    private DiagnosticoRepository repository;

    @InjectMocks
    private DiagnosticoService service;

    @Test
    void save_returnsSavedDiagnostico() {
        LocalDateTime fechaDiagnostico = LocalDateTime.now();
        Diagnostico nuevoDiagnostico = new Diagnostico(10L, "En mal estado", "pendiente", fechaDiagnostico, 1L);

        when(repository.save(any(Diagnostico.class))).thenReturn(nuevoDiagnostico);

        Diagnostico result = service.agregarDiagnostico(
            "En mal estado",
            "pendiente",
            fechaDiagnostico,
            1L
        );
        assertThat(result).isSameAs(nuevoDiagnostico);
    }

    @Test
    void obtenerTodos_returnsDiagnosticosList() {
        Diagnostico d1 = new Diagnostico(1L, "detalle1", "estado1", LocalDateTime.now(), 1L);
        Diagnostico d2 = new Diagnostico(2L, "detalle2", "estado2", LocalDateTime.now(), 2L);
        when(repository.findAll()).thenReturn(List.of(d1, d2));

        var result = service.obtenerTodos();

        assertThat(result).containsExactly(d1, d2);
    }

    @Test
    void obtenerPorId_returnsDiagnosticoIfExists() {
        Diagnostico d = new Diagnostico(1L, "detalle", "estado", LocalDateTime.now(), 1L);
        when(repository.findById(1L)).thenReturn(java.util.Optional.of(d));

        Diagnostico result = service.obtenerPorId(1L);

        assertThat(result).isEqualTo(d);
    }

    @Test
    void obtenerPorId_returnsNullIfNotExists() {
        when(repository.findById(99L)).thenReturn(java.util.Optional.empty());

        Diagnostico result = service.obtenerPorId(99L);

        assertThat(result).isNull();
    }

    @Test
    void actualizar_updatesAndReturnsDiagnosticoIfExists() {
        Diagnostico existente = new Diagnostico(1L, "detalle viejo", "estado viejo", LocalDateTime.now(), 1L);
        Diagnostico nuevo = new Diagnostico(1L, "detalle nuevo", "estado nuevo", LocalDateTime.now().plusDays(1), 1L);

        when(repository.findById(1L)).thenReturn(java.util.Optional.of(existente));
        when(repository.save(existente)).thenReturn(existente);

        Diagnostico result = service.actualizar(1L, nuevo);

        assertThat(result.getDetalle()).isEqualTo("detalle nuevo");
        assertThat(result.getEstado()).isEqualTo("estado nuevo");
        assertThat(result.getFechaDiagnostico()).isEqualTo(nuevo.getFechaDiagnostico());
    }

    @Test
    void actualizar_returnsNullIfDiagnosticoNotExists() {
        Diagnostico nuevo = new Diagnostico(1L, "detalle", "estado", LocalDateTime.now(), 1L);
        when(repository.findById(1L)).thenReturn(java.util.Optional.empty());

        Diagnostico result = service.actualizar(1L, nuevo);

        assertThat(result).isNull();
    }

    @Test
    void eliminarPorId_deletesIfExists() {
        when(repository.existsById(1L)).thenReturn(true);

        service.eliminarPorId(1L);

        org.mockito.Mockito.verify(repository).deleteById(1L);
    }

    @Test
    void eliminarPorId_doesNothingIfNotExists() {
        when(repository.existsById(2L)).thenReturn(false);

        service.eliminarPorId(2L);

        org.mockito.Mockito.verify(repository, org.mockito.Mockito.never()).deleteById(2L);
    }
}