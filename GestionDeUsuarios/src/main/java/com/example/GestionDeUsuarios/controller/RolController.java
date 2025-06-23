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
            @ApiResponse(responseCode = "200", description = "Lista de roles retornada con éxito", 
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = Rol.class))),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida")
        }
    )
    @GetMapping
    public ResponseEntity<List<Rol>> obtenerTodos() {
        return ResponseEntity.ok(RS.obtenerTodos());
    }

    @Operation(
        summary = "Crear nuevo rol",
        description = "Crea un nuevo rol en el sistema",
        responses = {
            @ApiResponse(responseCode = "201", description = "Rol creado correctamente", 
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = Rol.class))),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
        }
    )
    @PostMapping
    public ResponseEntity<Rol> crearRol(@RequestBody Rol rol) {
        Rol nuevo = RS.guardarRol(rol);
        return ResponseEntity.status(201).body(nuevo);
    }

    @Operation(
        summary = "Obtener rol por ID",
        description = "Busca un rol específico por su ID",
        responses = {
            @ApiResponse(responseCode = "200", description = "Rol encontrado", 
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = Rol.class))),
            @ApiResponse(responseCode = "404", description = "Rol no encontrado")
        }
    )
    @GetMapping("/{id}")
    public ResponseEntity<Rol> obtenerPorId(@PathVariable Long id) {
        Optional<Rol> rol = RS.obtenerPorId(id);
        return rol.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @Operation(
        summary = "Actualizar rol por ID",
        description = "Actualiza los datos de un rol existente por su ID",
        responses = {
            @ApiResponse(responseCode = "200", description = "Rol actualizado con éxito", 
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = Rol.class))),
            @ApiResponse(responseCode = "404", description = "Rol no encontrado")
        }
    )
    @PutMapping("/{id}")
    public ResponseEntity<Rol> actualizarRol(@PathVariable Long id, @RequestBody Rol datos) {
        Optional<Rol> actualizado = RS.actualizarRol(id, datos);
        return actualizado.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @Operation(
        summary = "Eliminar rol por ID",
        description = "Elimina un rol específico por su ID",
        responses = {
            @ApiResponse(responseCode = "204", description = "Rol eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Rol no encontrado")
        }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarRol(@PathVariable Long id) {
        boolean eliminado = RS.eliminarRol(id);
        return eliminado ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
