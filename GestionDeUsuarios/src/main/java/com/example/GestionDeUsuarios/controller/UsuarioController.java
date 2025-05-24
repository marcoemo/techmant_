package com.example.GestionDeUsuarios.controller;

import com.example.GestionDeUsuarios.model.Usuario;
import com.example.GestionDeUsuarios.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private final UsuarioService US;

    public UsuarioController(UsuarioService US) {
        this.US = US;
    }

    @PostMapping
    public ResponseEntity<String> crearUsuario(@RequestBody Usuario usu) {
        US.guardarUsuario(usu);
        return ResponseEntity.status(HttpStatus.CREATED).body("Usuario creado");
    }

    @GetMapping
    public List<Usuario> listaUsuarios() {
        return US.obtenerUsuarios();
    }

    @GetMapping("/correo/{correo}")
    public ResponseEntity<Usuario> obtenerPorCorreo(@PathVariable String correo) {
        Usuario usuario = US.obtenerPorCorreo(correo);
        if (usuario != null) {
            return ResponseEntity.ok(usuario);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}