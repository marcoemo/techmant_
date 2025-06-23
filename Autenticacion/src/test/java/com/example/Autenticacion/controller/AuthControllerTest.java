package com.example.Autenticacion.controller;

import com.example.Autenticacion.service.AuthService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService authService;

    @Test
    void autenticar_retorna200_siDatosCoinciden() throws Exception {
        when(authService.autenticarUsuario("lore@gmail.com", "abc12s3"))
            .thenReturn("Datos Coinciden");

        mockMvc.perform(post("/autenticacion")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "correo": "lore@gmail.com",
                        "contrasena": "abc12s3"
                    }
                """))
            .andExpect(status().isOk())
            .andExpect(content().string("Datos Coinciden"));
    }

    @Test
    void autenticar_retorna401_siContrasenaIncorrecta() throws Exception {
        when(authService.autenticarUsuario("lore@gmail.com", "malaClave"))
            .thenReturn("Contraseña no válida");

        mockMvc.perform(post("/autenticacion")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "correo": "lore@gmail.com",
                        "contrasena": "malaClave"
                    }
                """))
            .andExpect(status().isUnauthorized())
            .andExpect(content().string("Contraseña no válida"));
    }

    @Test
    void autenticar_retorna401_siUsuarioNoExiste() throws Exception {
        when(authService.autenticarUsuario("desconocido@mail.com", "algo"))
            .thenReturn("Usuario no encontrado");

        mockMvc.perform(post("/autenticacion")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "correo": "desconocido@mail.com",
                        "contrasena": "algo"
                    }
                """))
            .andExpect(status().isUnauthorized())
            .andExpect(content().string("Usuario no encontrado"));
    }

    @Test
    void autenticar_retorna401_siOcurreExcepcion() throws Exception {
        when(authService.autenticarUsuario("error@mail.com", "error"))
            .thenReturn("Error al autenticar: Error interno");

        mockMvc.perform(post("/autenticacion")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "correo": "error@mail.com",
                        "contrasena": "error"
                    }
                """))
            .andExpect(status().isUnauthorized())
            .andExpect(content().string("Error al autenticar: Error interno"));
    }
}
