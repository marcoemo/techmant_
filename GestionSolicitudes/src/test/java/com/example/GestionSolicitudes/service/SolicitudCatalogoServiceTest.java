package com.example.GestionSolicitudes.service;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

import java.util.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.GestionSolicitudes.client.CatalogoClient;
import com.example.GestionSolicitudes.model.Solicitud;
import com.example.GestionSolicitudes.model.SolicitudCatalogo;
import com.example.GestionSolicitudes.model.SolicitudCatalogoId;
import com.example.GestionSolicitudes.repository.SolicitudCatalogoRepository;
import com.example.GestionSolicitudes.repository.SolicitudRepository;

@ExtendWith(MockitoExtension.class)
public class SolicitudCatalogoServiceTest {
    @Mock
    private SolicitudCatalogoRepository repository;

    @Mock
    private SolicitudRepository solRepo;

    @Mock
    private CatalogoClient catalogoClient;

    @InjectMocks
    private SolicitudCatalogoService service;

    @Test
    void save_returnsSavedSolicitudCatalogo() {
        Solicitud solicitud = new Solicitud();
        solicitud.setSolicitudId(1L);

        SolicitudCatalogoId id = new SolicitudCatalogoId(1L, 2L);
        SolicitudCatalogo nuevoSolicitudCatalogo = new SolicitudCatalogo(id, solicitud, 10000);

        when(solRepo.findById(1L)).thenReturn(Optional.of(solicitud));
        Map<String, Object> catalogo = new HashMap<>();
        catalogo.put("id", 2L);
        when(catalogoClient.obtenerCatalogoPorId(2L)).thenReturn(catalogo);
        when(repository.save(nuevoSolicitudCatalogo)).thenReturn(nuevoSolicitudCatalogo);

        SolicitudCatalogo result = service.crearV(nuevoSolicitudCatalogo);

        assertThat(result).isSameAs(nuevoSolicitudCatalogo);
    }

    @Test
    void crearV_throwsException_whenSolicitudNotFound() {
        SolicitudCatalogoId id = new SolicitudCatalogoId(99L, 2L);
        SolicitudCatalogo relacion = new SolicitudCatalogo();
        relacion.setId(id);

        when(solRepo.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.crearV(relacion))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Solicitud no encontrada");
    }

    @Test
    void crearV_throwsException_whenCatalogoNotFound() {
        Solicitud solicitud = new Solicitud();
        solicitud.setSolicitudId(1L);

        SolicitudCatalogoId id = new SolicitudCatalogoId(1L, 99L);
        SolicitudCatalogo relacion = new SolicitudCatalogo();
        relacion.setId(id);

        when(solRepo.findById(1L)).thenReturn(Optional.of(solicitud));
        when(catalogoClient.obtenerCatalogoPorId(99L)).thenReturn(null);

        assertThatThrownBy(() -> service.crearV(relacion))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Catálogo no encontrado");
    }

    @Test
    void obtenerTodos_returnsList() {
        List<SolicitudCatalogo> lista = Arrays.asList(new SolicitudCatalogo(), new SolicitudCatalogo());
        when(repository.findAll()).thenReturn(lista);

        List<SolicitudCatalogo> result = service.obtenerTodos();

        assertThat(result).hasSize(2);
    }

    @Test
    void obtenerPorId_returnsOptional() {
        SolicitudCatalogoId id = new SolicitudCatalogoId(1L, 2L);
        SolicitudCatalogo sc = new SolicitudCatalogo();
        when(repository.findById(id)).thenReturn(Optional.of(sc));

        Optional<SolicitudCatalogo> result = service.obtenerPorId(id);

        assertThat(result).isPresent();
        assertThat(result.get()).isSameAs(sc);
    }

    @Test
    void agregarRelacion_returnsNull_whenSolicitudNotFound() {
        when(solRepo.findById(1L)).thenReturn(Optional.empty());

        SolicitudCatalogo result = service.agregarRelacion(1L, 2L, 100);

        assertThat(result).isNull();
    }

    @Test
    void agregarRelacion_savesAndReturnsSolicitudCatalogo() {
        Solicitud solicitud = new Solicitud();
        solicitud.setSolicitudId(1L);

        when(solRepo.findById(1L)).thenReturn(Optional.of(solicitud));
        when(repository.save(any(SolicitudCatalogo.class))).thenAnswer(inv -> inv.getArgument(0));

        SolicitudCatalogo result = service.agregarRelacion(1L, 2L, 500);

        assertThat(result).isNotNull();
        assertThat(result.getId().getSolicitudId()).isEqualTo(1L);
        assertThat(result.getId().getIdCatalogo()).isEqualTo(2L);
        assertThat(result.getSubtotal()).isEqualTo(500);
        assertThat(result.getSolicitud()).isSameAs(solicitud);
    }

    @Test
    void guardarSiNoExiste_insertsWhenNotExists() {
        Solicitud solicitud = new Solicitud();
        solicitud.setSolicitudId(1L);

        SolicitudCatalogoId id = new SolicitudCatalogoId(1L, 2L);

        when(repository.existsById(id)).thenReturn(false);
        when(solRepo.findById(1L)).thenReturn(Optional.of(solicitud));
        when(repository.save(any(SolicitudCatalogo.class))).thenAnswer(inv -> inv.getArgument(0));

        service.guardarSiNoExiste(1L, 2L, 1000);

        verify(repository).save(any(SolicitudCatalogo.class));
    }

    @Test
    void guardarSiNoExiste_doesNotInsertWhenExists() {
        SolicitudCatalogoId id = new SolicitudCatalogoId(1L, 2L);
        when(repository.existsById(id)).thenReturn(true);

        service.guardarSiNoExiste(1L, 2L, 1000);

        verify(repository, never()).save(any());
    }
}