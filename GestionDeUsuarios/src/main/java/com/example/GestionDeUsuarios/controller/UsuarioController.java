package com.example.GestionDeUsuarios.controller;

import com.example.GestionDeUsuarios.model.Usuario;
import com.example.GestionDeUsuarios.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private final UsuarioService US;

    public UsuarioController(UsuarioService US) {
        this.US = US;
    }

    // 🟩 Crear usuario
    @PostMapping
    public ResponseEntity<String> crearUsuario(@RequestBody Usuario usu) {
        String resultado = US.guardarUsuario(usu);
        if (resultado.contains("Ya existe")) {
            return ResponseEntity.status(409).body(resultado);
        }
        return ResponseEntity.status(201).body(resultado);
    }

    // 🟦 Listar todos los usuarios
    @GetMapping
    public List<Usuario> listaUsuarios() {
        return US.obtenerUsuarios();
    }

    // 🟨 Buscar usuario por correo
    @GetMapping("/correo/{correo}")
    public ResponseEntity<Usuario> obtenerPorCorreo(@PathVariable String correo) {
        Optional<Usuario> usuario = US.buscarPorCorreo(correo);
        return usuario.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // 🟥 Eliminar usuario por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable Long id) {
        boolean eliminado = US.eliminarUsuario(id);
        if (eliminado) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // 🟧 Editar usuario por ID
    @PutMapping("/{id}")
    public ResponseEntity<?> editarUsuario(@PathVariable Long id, @RequestBody Usuario nuevo) {
        Optional<Usuario> actualizado = US.editarUsuario(id, nuevo);
        return actualizado.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
}
