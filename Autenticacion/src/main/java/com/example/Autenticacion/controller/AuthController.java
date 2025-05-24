package com.example.Autenticacion.controller;

import com.example.Autenticacion.model.Auth;
import com.example.Autenticacion.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/autenticacion")
public class AuthController {

    @Autowired
    private AuthService authService;

    // Ahora se espera un JSON en el body con "correo" y "contrasena"
    @PostMapping
    public ResponseEntity<String> autenticar(@RequestBody Auth auth) {
        String resultado = authService.autenticarUsuario(auth.getCorreo(), auth.getContrasena());

        if (resultado.equals("Autenticación exitosa")) {
            return ResponseEntity.ok(resultado);
        } else {
            return ResponseEntity.status(401).body(resultado);
        }
    }
}