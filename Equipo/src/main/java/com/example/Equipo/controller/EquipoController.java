package com.example.Equipo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.Equipo.model.Equipo;
import com.example.Equipo.service.EquipoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/equipos")
public class EquipoController {

    @Autowired
    private EquipoService svc;

    // Crear un nuevo equipo -- cliente
    @Operation(
        summary = "Crear un nuevo equipo",
        description = "Crea un nuevo equipo en el sistema",
        responses = {
            @ApiResponse(responseCode = "200", description = "Equipo creado",
                content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Equipo.class))),
            @ApiResponse(responseCode = "404", description = "Datos de entrada inválidos")
        }
    )
    @PostMapping
    public Equipo crear(@RequestBody Equipo e) {
        return svc.crear(e);
    }

    @Operation(
        summary = "Listar todos los equipos",
        description = "Obtiene una lista de todos los equipos",
        responses = {
            @ApiResponse(responseCode = "200", description = "Lista de equipos",
                content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Equipo.class)))
        }
    )
    @GetMapping
    public List<Equipo> listar() {
        return svc.obtenerTodos();
    }

    @Operation(
        summary = "Obtener un equipo por id",
        description = "Obtiene los detalles de un equipo específico por su id",
        responses = {
            @ApiResponse(responseCode = "200", description = "Equipo encontrado",
                content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Equipo.class))),
            @ApiResponse(responseCode = "404", description = "Equipo no encontrado")
        }
    )
    @GetMapping("/{id}")
    public ResponseEntity<Equipo> obtener(@PathVariable Long id) {
        Equipo e = svc.obtenerPorId(id);
        return e != null ? ResponseEntity.ok(e) : ResponseEntity.notFound().build();
    }

    @Operation(
        summary = "Buscar equipos por usuario",
        description = "Obtiene una lista de equipos asociados a un usuario",
        responses = {
            @ApiResponse(responseCode = "200", description = "Lista de equipos del usuario",
                content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Equipo.class)))
        }
    )
    @GetMapping("/usuario/{usuarioId}")
    public List<Equipo> buscarPorUsuario(@PathVariable Long usuarioId) {
        return svc.buscarPorUsuario(usuarioId);
    }

    @Operation(
        summary = "Actualizar un equipo por id",
        description = "Actualiza los datos de un equipo existente por su id",
        responses = {
            @ApiResponse(responseCode = "200", description = "Equipo actualizado",
                content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Equipo.class))),
            @ApiResponse(responseCode = "404", description = "Equipo no encontrado")
        }
    )
    @PutMapping("/{id}")
    public ResponseEntity<Equipo> actualizar(@PathVariable Long id, @RequestBody Equipo e) {
        Equipo actualizado = svc.actualizar(id, e);
        return actualizado != null ? ResponseEntity.ok(actualizado) : ResponseEntity.notFound().build();
    }

    @Operation(
        summary = "Eliminar un equipo por id",
        description = "Elimina un equipo existente por su id",
        responses = {
            @ApiResponse(responseCode = "204", description = "Equipo eliminado"),
            @ApiResponse(responseCode = "404", description = "Equipo no encontrado")
        }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        svc.eliminarPorId(id);
        return ResponseEntity.noContent().build();
    }
}