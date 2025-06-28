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

    @Operation(
        summary = "Obtener técnicos con usuario y solicitud",
        description = "Obtiene la lista de técnicos junto con sus IDs de usuario y solicitud",
        responses = {
            @ApiResponse(responseCode = "200", description = "Lista de técnicos con usuario y solicitud",
                content = @Content(mediaType = "application/json"))
        }
    )
    @GetMapping("/con-usuario-solicitud")
    public List<Map<String, Object>> obtenerTecnicosConUsuarioYSolicitud() {
        List<Tecnico> tecnicos = AS.obtenerTodos();
        List<Map<String, Object>> resultado = new java.util.ArrayList<>();
        for (Tecnico tecnico : tecnicos) {
            Map<String, Object> map = new java.util.HashMap<>();
            map.put("tecnico", tecnico);
            map.put("usuarioId", tecnico.getUsuarioId());
            map.put("solicitudId", tecnico.getSolicitudId());
            resultado.add(map);
        }
        return resultado;
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
    if (nuevaDisponibilidad == null) {
        return ResponseEntity.badRequest().body("Falta el campo 'nuevaDisponibilidad'");
    }
    boolean modificado = AS.modificarDisponibilidad(id, nuevaDisponibilidad);
    if (modificado) {
        return ResponseEntity.ok("Tecnico modificado");
    } else {
        return ResponseEntity.notFound().build();
    }
}

    @Operation(
        summary = "Agregar un nuevo técnico",
        description = "Agrega un nuevo técnico al sistema",
        responses = {
            @ApiResponse(responseCode = "201", description = "Técnico agregado",
                content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Tecnico.class))),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
        }
    )

    @PostMapping
    public ResponseEntity<?> agregarTecnico(@RequestBody Map<String, Object> body) {
    try {
        String disponibilidad = (String) body.get("disponibilidad");
        Object usuarioIdObj = body.get("usuarioId");
        Object solicitudIdObj = body.get("solicitudId");

        if (disponibilidad == null || usuarioIdObj == null || solicitudIdObj == null) {
            return ResponseEntity.badRequest().body("Faltan campos obligatorios");
        }

        Long usuarioId = Long.parseLong(usuarioIdObj.toString());
        Long solicitudId = Long.parseLong(solicitudIdObj.toString());

        Tecnico tecnico = AS.agregarTecnico(disponibilidad, usuarioId, solicitudId);
        if (tecnico == null) {
            return ResponseEntity.status(409).body("Ya existe un técnico con ese usuarioId y solicitudId");
        }
        return ResponseEntity.status(201).body(tecnico);
    } catch (Exception e) {
        return ResponseEntity.badRequest().body("Datos de entrada inválidos: " + e.getMessage());
    }
}

}