package com.example.Seguimiento.controller;

import com.example.Seguimiento.model.Seguimiento;
import com.example.Seguimiento.service.SeguimientoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/seguimientos")
public class SeguimientoController {

    private final SeguimientoService service;

    public SeguimientoController(SeguimientoService service) {
        this.service = service;
    }

    @Operation(
        summary = "Obtener todos los seguimientos",
        description = "Devuelve una lista con todos los registros de seguimiento",
        responses = {
            @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente",
                content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = Seguimiento.class)))
        }
    )
    @GetMapping
    public List<Seguimiento> obtenerTodos() {
        return service.obtenerTodos();
    }

    @Operation(
        summary = "Obtener seguimiento por ID",
        description = "Busca un seguimiento específico usando su ID",
        responses = {
            @ApiResponse(responseCode = "200", description = "Seguimiento encontrado",
                content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = Seguimiento.class))),
            @ApiResponse(responseCode = "404", description = "Seguimiento no encontrado")
        }
    )
    @GetMapping("/{id}")
    public ResponseEntity<Seguimiento> obtenerPorId(@PathVariable Long id) {
        return service.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
        summary = "Crear nuevo seguimiento",
        description = "Crea y guarda un nuevo seguimiento",
        responses = {
            @ApiResponse(responseCode = "201", description = "Seguimiento creado exitosamente",
                content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = Seguimiento.class))),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
        }
    )
    @PostMapping
    public ResponseEntity<Seguimiento> crear(@RequestBody Seguimiento nuevo) {
        return ResponseEntity.status(201).body(service.crear(nuevo));
    }

    @Operation(
        summary = "Actualizar seguimiento por ID",
        description = "Actualiza un seguimiento existente con nuevos datos",
        responses = {
            @ApiResponse(responseCode = "200", description = "Seguimiento actualizado",
                content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = Seguimiento.class))),
            @ApiResponse(responseCode = "404", description = "Seguimiento no encontrado")
        }
    )
    @PutMapping("/{id}")
    public ResponseEntity<Seguimiento> actualizar(@PathVariable Long id, @RequestBody Seguimiento nuevo) {
        return service.actualizar(id, nuevo)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
        summary = "Eliminar seguimiento por ID",
        description = "Elimina un seguimiento específico",
        responses = {
            @ApiResponse(responseCode = "204", description = "Seguimiento eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Seguimiento no encontrado")
        }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
        summary = "Obtener seguimientos por ID de solicitud",
        description = "Devuelve todos los seguimientos vinculados a una solicitud específica",
        responses = {
            @ApiResponse(responseCode = "200", description = "Seguimientos encontrados",
                content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = Seguimiento.class)))
        }
    )
    @GetMapping("/solicitud/{solicitudId}")
    public List<Seguimiento> porSolicitud(@PathVariable Long solicitudId) {
        return service.filtrarPorSolicitud(solicitudId);
    }
}
