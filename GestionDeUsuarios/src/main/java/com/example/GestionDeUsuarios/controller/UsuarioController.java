package com.example.GestionDeUsuarios.controller;

import com.example.GestionDeUsuarios.model.Usuario;
import com.example.GestionDeUsuarios.service.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

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

    @Operation(
        summary = "Crear un nuevo usuario",
        description = "Crea un usuario nuevo si el correo no está registrado. Retorna error si ya existe un usuario con ese correo.",
        responses = {
            @ApiResponse(responseCode = "201", description = "Usuario creado exitosamente",
                content = @Content(mediaType = "text/plain")),
            @ApiResponse(responseCode = "409", description = "Conflicto: El correo ya está registrado",
                content = @Content(mediaType = "text/plain"))
        }
    )
    @PostMapping
    public ResponseEntity<String> crearUsuario(@RequestBody Usuario usu) {
        String resultado = US.guardarUsuario(usu);
        if (resultado.contains("Ya existe")) {
            return ResponseEntity.status(409).body(resultado);
        }
        return ResponseEntity.status(201).body(resultado);
    }

    @Operation(
        summary = "Obtener todos los usuarios",
        description = "Retorna la lista completa de usuarios en el sistema.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Lista de usuarios retornada con éxito",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = Usuario.class))),
            @ApiResponse(responseCode = "204", description = "No hay usuarios registrados")
        }
    )
    @GetMapping
    public List<Usuario> listaUsuarios() {
        return US.obtenerUsuarios();
    }

    @Operation(
        summary = "Buscar usuario por correo",
        description = "Busca y retorna un usuario específico mediante su correo electrónico.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Usuario encontrado",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = Usuario.class))),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
        }
    )
    @GetMapping("/correo/{correo}")
    public ResponseEntity<Usuario> obtenerPorCorreo(@PathVariable String correo) {
        Optional<Usuario> usuario = US.buscarPorCorreo(correo);
        return usuario.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @Operation(
        summary = "Eliminar usuario por ID",
        description = "Elimina un usuario dado su ID. Retorna 204 si fue eliminado o 404 si no existe.",
        responses = {
            @ApiResponse(responseCode = "204", description = "Usuario eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
        }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable Long id) {
        boolean eliminado = US.eliminarUsuario(id);
        if (eliminado) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(
        summary = "Editar usuario por ID",
        description = "Actualiza los datos de un usuario existente identificado por su ID.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Usuario actualizado",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = Usuario.class))),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
        }
    )
    @PutMapping("/{id}")
    public ResponseEntity<?> editarUsuario(@PathVariable Long id, @RequestBody Usuario nuevo) {
        Optional<Usuario> actualizado = US.editarUsuario(id, nuevo);
        return actualizado.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
}
