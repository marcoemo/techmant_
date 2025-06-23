package com.example.Diagnosticos.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.Diagnosticos.model.Diagnostico;
import com.example.Diagnosticos.service.DiagnosticoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/diagnosticos")
public class DiagnosticoController {

    @Autowired
    private DiagnosticoService svc;

    // Crear un nuevo diagnostico tecnico
    @Operation(
        summary = "Crear un nuevo diagnóstico",
        description = "Crea un nuevo diagnóstico en el sistema",
        responses = {
            @ApiResponse(responseCode = "200", description = "Diagnóstico creado",
                content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Diagnostico.class))),
            @ApiResponse(responseCode = "404", description = "Datos de entrada inválidos")
        }
    )
    @PostMapping
    public Diagnostico agregarDiagnostico(@RequestBody Map<String, String> body) {
        String diagnosticoId = body.get("diagnosticoId");
        String detalle = body.get("detalle");
        java.time.LocalDateTime fecha = java.time.LocalDateTime.parse(body.get("fecha"));
        Long idAsignacion = Long.parseLong(body.get("idAsignacion"));

        return svc.agregarDiagnostico(diagnosticoId, detalle, fecha, idAsignacion);
    }

    @Operation(
        summary = "Listar todos los diagnósticos",
        description = "Obtiene una lista de todos los diagnósticos",
        responses = {
            @ApiResponse(responseCode = "200", description = "Lista de diagnósticos",
                content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Diagnostico.class)))
        }
    )
    @GetMapping
    public List<Diagnostico> listar() {
        return svc.obtenerTodos();
    }

    @Operation(
        summary = "Obtener un diagnóstico por ID",
        description = "Obtiene un diagnóstico específico por su ID",
        responses = {
            @ApiResponse(responseCode = "200", description = "Diagnóstico encontrado",
                content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Diagnostico.class))),
            @ApiResponse(responseCode = "404", description = "Diagnóstico no encontrado")
        }
    )
    @GetMapping("/{id}")
    public ResponseEntity<Diagnostico> obtener(@PathVariable Long id) {
        Diagnostico d = svc.obtenerPorId(id);
        return d != null ? ResponseEntity.ok(d) : ResponseEntity.notFound().build();
    }

    @Operation(
        summary = "Actualizar un diagnóstico por ID",
        description = "Actualiza los datos de un diagnóstico existente por su ID",
        responses = {
            @ApiResponse(responseCode = "200", description = "Diagnóstico actualizado",
                content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Diagnostico.class))),
            @ApiResponse(responseCode = "404", description = "Diagnóstico no encontrado")
        }
    )
    @PutMapping("/{id}")
    public ResponseEntity<Diagnostico> actualizar(@PathVariable Long id, @RequestBody Diagnostico d) {
        Diagnostico actualizado = svc.actualizar(id, d);
        return actualizado != null ? ResponseEntity.ok(actualizado) : ResponseEntity.notFound().build();
    }

    @Operation(
        summary = "Eliminar un diagnóstico por ID",
        description = "Elimina un diagnóstico existente por su ID",
        responses = {
            @ApiResponse(responseCode = "204", description = "Diagnóstico eliminado"),
            @ApiResponse(responseCode = "404", description = "Diagnóstico no encontrado")
        }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        svc.eliminarPorId(id);
        return ResponseEntity.noContent().build();
    }
}
