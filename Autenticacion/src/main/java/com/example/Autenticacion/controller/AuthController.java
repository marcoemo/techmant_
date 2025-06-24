package com.example.Autenticacion.controller;

import com.example.Autenticacion.model.Auth;
import com.example.Autenticacion.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
@RestController
@RequestMapping("/autenticacion")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Operation(
        summary = "Autenticar usuario",
        description = "Autentica un usuario en el sistema",
        responses = {
            @ApiResponse(responseCode = "200", description = "Usuario autenticado",
                content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Auth.class))),
            @ApiResponse(responseCode = "401", description = "Credenciales inválidas")
        }
    )
    @PostMapping
    public ResponseEntity<String> autenticar(@RequestBody Auth auth) {
        String resultado = authService.autenticarUsuario(auth.getCorreo(), auth.getContrasena());

        if ("Datos Coinciden".equals(resultado)) {
            return ResponseEntity.ok(resultado);
        } else {
            return ResponseEntity.status(401).body(resultado);
        }
    }
}
