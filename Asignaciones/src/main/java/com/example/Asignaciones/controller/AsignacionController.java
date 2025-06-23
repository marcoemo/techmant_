package com.example.Asignaciones.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Asignaciones.model.Tecnico;
import com.example.Asignaciones.service.AsignacionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("asignacion")
public class AsignacionController {

    @Autowired
    private AsignacionService AS;

    // Obtener todos los técnicos
    @Operation(
        summary = "Obtener todos los técnicos",
        description = "Obtiene la lista de todos los técnicos en el sistema",
        responses = {
            @ApiResponse(responseCode = "200", description = "Lista de técnicos",
                content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Tecnico.class)))
        }
    )
    @GetMapping
    public List<Tecnico> obtenerTodos() {
        return AS.obtenerTodos();
    }

    // Obtener técnico por ID (cambiado por el web client)
    @Operation(
        summary = "Obtener técnico por ID",
        description = "Obtiene un técnico específico por su ID",
        responses = {
            @ApiResponse(responseCode = "200", description = "Técnico encontrado",
                content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Tecnico.class))),
            @ApiResponse(responseCode = "404", description = "Técnico no encontrado")
        }
    )
    @GetMapping("/{id}")
    public ResponseEntity<Tecnico> buscarPorId(@PathVariable Long id) {
        Tecnico tecnico = AS.buscarPorId(id);
        return tecnico != null ? ResponseEntity.ok(tecnico) : ResponseEntity.notFound().build();
    }

    // Eliminar técnico por ID
    @Operation(
        summary = "Eliminar técnico por ID",
        description = "Elimina un técnico específico por su ID",
        responses = {
            @ApiResponse(responseCode = "200", description = "Técnico eliminado"),
            @ApiResponse(responseCode = "404", description = "Técnico no encontrado")
        }
    )
    @DeleteMapping("/{id}")
    public void eliminarPorId(@PathVariable Long id) {
        AS.eliminarPorId(id);
    }

    // Modificar disponibilidad
    @Operation(
        summary = "Modificar disponibilidad de técnico",
        description = "Modifica la disponibilidad de un técnico por su ID",
        responses = {
            @ApiResponse(responseCode = "200", description = "Disponibilidad modificada"),
            @ApiResponse(responseCode = "404", description = "Técnico no encontrado")
        }
    )
    @PutMapping("/{id}/disponibilidad")
    public ResponseEntity<String> modificarDisponibilidad(@PathVariable Long id, @RequestBody Map<String, String> body) {
        String nuevaDisponibilidad = body.get("nuevaDisponibilidad");
        AS.modificarDisponibilidad(id, nuevaDisponibilidad);
        return ResponseEntity.ok("Tecnico modificado");
    }

    @Operation(
        summary = "Agregar un nuevo técnico",
        description = "Agrega un nuevo técnico al sistema",
        responses = {
            @ApiResponse(responseCode = "200", description = "Técnico agregado",
                content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Tecnico.class))),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
        }
    )
    @PostMapping
    public Tecnico agregarTecnico(@RequestBody Map<String, String> body) {
        String nombre = body.get("nombre");
        String disponibilidad = body.get("disponibilidad");
        Long usuarioId = Long.parseLong(body.get("usuarioId"));
        Long solicitudId = Long.parseLong(body.get("solicitudId"));
        return AS.agregarTecnico(nombre, disponibilidad, usuarioId, solicitudId);
    }
}