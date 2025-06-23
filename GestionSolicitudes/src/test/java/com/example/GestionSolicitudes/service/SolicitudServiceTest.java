package com.example.GestionSolicitudes.service;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.GestionSolicitudes.model.Solicitud;
import com.example.GestionSolicitudes.repository.SolicitudRepository;

@ExtendWith(MockitoExtension.class)
public class SolicitudServiceTest {
    @Mock
    private SolicitudRepository repository;

    @InjectMocks
    private SolicitudService service;

    @Test
    void save_returnsSavedDiagnostico() {
        LocalDate fechaSolicitud = LocalDate.now();
        Solicitud nuevaSolicitud = new Solicitud();
        nuevaSolicitud.setDescripcionProblema("ds");
        nuevaSolicitud.setFechaCreacion(fechaSolicitud);
        nuevaSolicitud.setFechaCierre(null);
        nuevaSolicitud.setEstado("pendiente");
        nuevaSolicitud.setUsuarioId(1L);

        when(repository.save(any(Solicitud.class))).thenReturn(nuevaSolicitud);

        Solicitud result = service.agregarSolicitud(
            "ds",
            fechaSolicitud,
            null,
            "pendiente",
            1L
        );
        assertThat(result).usingRecursiveComparison().isEqualTo(nuevaSolicitud);
    }

    @Test
    void obtenerTodas_returnsListOfSolicitudes() {
        Solicitud s1 = new Solicitud();
        Solicitud s2 = new Solicitud();
        List<Solicitud> solicitudes = Arrays.asList(s1, s2);

        when(repository.findAll()).thenReturn(solicitudes);

        List<Solicitud> result = service.obtenerTodas();
        assertThat(result).hasSize(2).containsExactly(s1, s2);
    }

    @Test
    void obtenerPorId_returnsSolicitudIfExists() {
        Solicitud s = new Solicitud();
        s.setSolicitudId(10L);

        when(repository.findById(10L)).thenReturn(Optional.of(s));

        Optional<Solicitud> result = service.obtenerPorId(10L);
        assertThat(result).isPresent().contains(s);
    }

    @Test
    void obtenerPorId_returnsEmptyIfNotExists() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        Optional<Solicitud> result = service.obtenerPorId(99L);
        assertThat(result).isEmpty();
    }

    @Test
    void actualizar_updatesAndReturnsSolicitud() {
        Solicitud existente = new Solicitud();
        existente.setSolicitudId(1L);
        existente.setDescripcionProblema("old");
        existente.setUsuarioId(1L);
        existente.setEstado("pendiente");

        Solicitud nueva = new Solicitud();
        nueva.setDescripcionProblema("new");
        nueva.setUsuarioId(2L);
        nueva.setEstado("cerrado");

        when(repository.findById(1L)).thenReturn(Optional.of(existente));
        when(repository.save(any(Solicitud.class))).thenAnswer(inv -> inv.getArgument(0));

        Optional<Solicitud> result = service.actualizar(1L, nueva);

        assertThat(result).isPresent();
        assertThat(result.get().getDescripcionProblema()).isEqualTo("new");
        assertThat(result.get().getUsuarioId()).isEqualTo(2L);
        assertThat(result.get().getEstado()).isEqualTo("cerrado");
    }

    @Test
    void actualizar_returnsEmptyIfNotFound() {
        Solicitud nueva = new Solicitud();
        when(repository.findById(123L)).thenReturn(Optional.empty());

        Optional<Solicitud> result = service.actualizar(123L, nueva);
        assertThat(result).isEmpty();
    }

    @Test
    void eliminar_deletesIfExists() {
        when(repository.existsById(5L)).thenReturn(true);

        boolean result = service.eliminar(5L);

        verify(repository).deleteById(5L);
        assertThat(result).isTrue();
    }

    @Test
    void eliminar_returnsFalseIfNotExists() {
        when(repository.existsById(6L)).thenReturn(false);

        boolean result = service.eliminar(6L);

        verify(repository, never()).deleteById(anyLong());
        assertThat(result).isFalse();
    }

    @Test
    void filtrarPorUsuario_returnsSolicitudesForUser() {
        Solicitud s1 = new Solicitud();
        Solicitud s2 = new Solicitud();
        List<Solicitud> solicitudes = Arrays.asList(s1, s2);

        when(repository.findByUsuarioId(7L)).thenReturn(solicitudes);

        List<Solicitud> result = service.filtrarPorUsuario(7L);
        assertThat(result).containsExactly(s1, s2);
    }
}