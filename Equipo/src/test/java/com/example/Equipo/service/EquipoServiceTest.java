package com.example.Equipo.service;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.Equipo.model.Categoria;
import com.example.Equipo.model.Equipo;
import com.example.Equipo.repository.EquipoRepository;


@ExtendWith(MockitoExtension.class)
public class EquipoServiceTest {
    @Mock
    private EquipoRepository repository;

    @InjectMocks
    private EquipoService service;

    @Test
    void save_returnsSavedEquipo() {
        Categoria categoria = new Categoria();
        Equipo nuevoEquipo = new Equipo(1L, categoria, "Marca1", "Modelo1", "123456789", 1L);

        when(repository.save(nuevoEquipo)).thenReturn(nuevoEquipo);

        Equipo result = service.crear(nuevoEquipo);

        assertThat(result).isSameAs(nuevoEquipo);
    }

    @Test
    void obtenerTodos_returnsListOfEquipos() {
        Equipo equipo = new Equipo(1L, new Categoria(), "Marca1", "Modelo1", "SN1", 1L);
        when(repository.findAll()).thenReturn(List.of(equipo));

        var result = service.obtenerTodos();

        assertThat(result).containsExactly(equipo);
    }

    @Test
    void obtenerPorId_returnsEquipoIfExists() {
        Equipo equipo = new Equipo(1L, new Categoria(), "Marca", "Modelo", "SN", 1L);
        when(repository.findById(1L)).thenReturn(java.util.Optional.of(equipo));

        Equipo result = service.obtenerPorId(1L);

        assertThat(result).isEqualTo(equipo);
    }

    @Test
    void obtenerPorId_returnsNullIfNotExists() {
        when(repository.findById(99L)).thenReturn(java.util.Optional.empty());

        Equipo result = service.obtenerPorId(99L);

        assertThat(result).isNull();
    }

    @Test
    void buscarPorUsuario_returnsEquiposWithMatchingUsuarioId() {
        Equipo equipo1 = new Equipo(1L, new Categoria(), "Marca1", "Modelo1", "SN1", 10L);
        Equipo equipo2 = new Equipo(2L, new Categoria(), "Marca2", "Modelo2", "SN2", 20L);
        Equipo equipo3 = new Equipo(3L, new Categoria(), "Marca3", "Modelo3", "SN3", 10L);
        when(repository.findAll()).thenReturn(List.of(equipo1, equipo2, equipo3));

        var result = service.buscarPorUsuario(10L);

        assertThat(result).containsExactly(equipo1, equipo3);
    }

    @Test
    void actualizar_updatesAndReturnsEquipoIfExists() {
        Equipo existente = new Equipo(1L, new Categoria(), "OldMarca", "OldModelo", "OldSN", 1L);
        Equipo nuevo = new Equipo(1L, new Categoria(), "NewMarca", "NewModelo", "NewSN", 2L);

        when(repository.findById(1L)).thenReturn(java.util.Optional.of(existente));
        when(repository.save(existente)).thenReturn(existente);

        Equipo result = service.actualizar(1L, nuevo);

        assertThat(result.getMarca()).isEqualTo("NewMarca");
        assertThat(result.getModelo()).isEqualTo("NewModelo");
        assertThat(result.getNumeroSerie()).isEqualTo("NewSN");
        assertThat(result.getIdUsuario()).isEqualTo(2L);
    }

    @Test
    void actualizar_returnsNullIfEquipoNotExists() {
        Equipo nuevo = new Equipo(1L, new Categoria(), "NewMarca", "NewModelo", "NewSN", 2L);

        when(repository.findById(1L)).thenReturn(java.util.Optional.empty());

        Equipo result = service.actualizar(1L, nuevo);

        assertThat(result).isNull();
    }

    @Test
    void eliminarPorId_deletesEquipoIfExists() {
        when(repository.existsById(1L)).thenReturn(true);

        service.eliminarPorId(1L);

        org.mockito.Mockito.verify(repository).deleteById(1L);
    }

    @Test
    void eliminarPorId_doesNothingIfEquipoNotExists() {
        when(repository.existsById(1L)).thenReturn(false);

        service.eliminarPorId(1L);

        org.mockito.Mockito.verify(repository, org.mockito.Mockito.never()).deleteById(1L);
    }
}
