package com.example.GestionDeUsuarios.controller;

import com.example.GestionDeUsuarios.model.Rol;
import com.example.GestionDeUsuarios.service.RolService;

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
@RequestMapping("/roles")
public class RolController {

    @Autowired
    private final RolService RS;

    public RolController(RolService RS) {
        this.RS = RS;
    }

    @Operation(
        summary = "Obtener todos los roles",
        description = "Obtiene una lista de todos los roles del sistema",
        responses = {
            @ApiResponse(responseCode = "200", description = "OK", 
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = Rol.class))),
            @ApiResponse(responseCode = "400", description = "Entrada inválida")
        }
    )
    @GetMapping
    public ResponseEntity<List<Rol>> obtenerTodos() {
        return ResponseEntity.ok(RS.obtenerTodos());
    }

    @Operation(summary = "Crear nuevo rol",
        description = "Crea un nuevo rol",
        responses = {
                @ApiResponse(responseCode = "200", description = "OK", 
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = Rol.class))),
            @ApiResponse(responseCode = "400", description = "Entrada inválida")
        })
    @PostMapping
    public ResponseEntity<Rol> crearRol(@RequestBody Rol rol) {
        Rol nuevo = RS.guardarRol(rol);
        return ResponseEntity.status(201).body(nuevo);
    }

    @Operation(summary = "Obtener rol por ID")
    @GetMapping("/{id}")
    public ResponseEntity<Rol> obtenerPorId(@PathVariable Long id) {
        Optional<Rol> rol = RS.obtenerPorId(id);
        return rol.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Actualizar rol por ID")
    @PutMapping("/{id}")
    public ResponseEntity<Rol> actualizarRol(@PathVariable Long id, @RequestBody Rol datos) {
        Optional<Rol> actualizado = RS.actualizarRol(id, datos);
        return actualizado.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Eliminar rol por ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarRol(@PathVariable Long id) {
        boolean eliminado = RS.eliminarRol(id);
        return eliminado ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
