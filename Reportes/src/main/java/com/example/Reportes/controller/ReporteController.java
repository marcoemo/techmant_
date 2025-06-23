package com.example.Reportes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.Reportes.model.Reporte;
import com.example.Reportes.service.ReporteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/reportes")
public class ReporteController {

    @Autowired
    private ReporteService svc;
    // Crear un nuevo reporte -- cliente
    
    @Operation(
        summary = "Crear un Reporte",
        description = "Agrega una nuevo reporte de los diagnosticos",
        responses = {
            @ApiResponse(responseCode = "200", description = "Reporte creada",
                content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Reporte.class))),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
        }
    )
    @PostMapping
    public ResponseEntity<Reporte> agregarReporte(@RequestBody Reporte reporte) {
        Reporte nuevo = svc.agregarReporte(
            reporte.getFechaGeneracion(),
            reporte.getCostoManoObra(),
            reporte.getDiagnosticoId()
        );
        return ResponseEntity.ok(nuevo);
    }

    @Operation(
        summary = "Obtener todos los Reportes",
        description = "Devuelve una lista de todos los reportes",
        responses = {
            @ApiResponse(responseCode = "200", description = "Lista de reportes",
                content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Reporte.class)))
        }
    )
    @GetMapping
    public List<Reporte> listar() {
        return svc.obtenerTodos();
    }

    @Operation(
        summary = "Obtener un Reporte por ID",
        description = "Devuelve un reporte específico por su ID",
        responses = {
            @ApiResponse(responseCode = "200", description = "Reporte encontrado",
                content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Reporte.class))),
            @ApiResponse(responseCode = "404", description = "Reporte no encontrado")
        }
    )
    @GetMapping("/{id}")
    public ResponseEntity<Reporte> obtener(@PathVariable Long id) {
        Reporte r = svc.obtenerPorId(id);
        return r != null ? ResponseEntity.ok(r) : ResponseEntity.notFound().build();
    }

    @Operation(
        summary = "Eliminar un Reporte por ID",
        description = "Elimina un reporte específico por su ID",
        responses = {
            @ApiResponse(responseCode = "204", description = "Reporte eliminado"),
            @ApiResponse(responseCode = "404", description = "Reporte no encontrado")
        }
    )
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminarReporte(@PathVariable Long id) {
        svc.eliminarPorId(id);
    }
}
