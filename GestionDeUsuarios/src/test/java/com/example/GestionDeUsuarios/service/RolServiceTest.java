package com.example.GestionDeUsuarios.service;

import com.example.GestionDeUsuarios.model.Rol;
import com.example.GestionDeUsuarios.repository.RolRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RolServiceTest {

    @Mock
    private RolRepository RP;

    @InjectMocks
    private RolService rolService;

    @Test
    void obtenerTodos_devuelveListaDeRoles() {
        List<Rol> lista = List.of(new Rol(1L, "ADMINISTRADOR"), new Rol(2L, "USUARIO"));
        when(RP.findAll()).thenReturn(lista);

        List<Rol> result = rolService.obtenerTodos();

        assertThat(result).hasSize(2).containsExactlyElementsOf(lista);
    }

    @Test
    void cargarRolesIniciales_creaSoloLosQueFaltan() {
        when(RP.existsById(1L)).thenReturn(true);
        when(RP.existsById(2L)).thenReturn(false);
        when(RP.existsById(3L)).thenReturn(false);
        when(RP.existsById(4L)).thenReturn(true);
        when(RP.existsById(5L)).thenReturn(false);

        rolService.CargarRolesIniciales();

        verify(RP, times(1)).save(new Rol(2L, "TECNICO"));
        verify(RP, times(1)).save(new Rol(3L, "SUPERVISOR"));
        verify(RP, times(1)).save(new Rol(5L, "USUARIO"));

        verify(RP, never()).save(new Rol(1L, "ADMINISTRADOR"));
        verify(RP, never()).save(new Rol(4L, "SOPORTE"));
    }
}
