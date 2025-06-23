package com.example.GestionSolicitudes.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.GestionSolicitudes.model.Solicitud;
import com.example.GestionSolicitudes.service.SolicitudService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/solicitudes")
public class SolicitudController {

    private final SolicitudService svc;

    public SolicitudController(SolicitudService svc) {
        this.svc = svc;
    }

    //tecnico -- supervisor
    @Operation(
        summary = "Obtener todas las solicitudes",
        description = "Obtiene una lista de todas las solicitudes registradas en el sistema",
        responses = {
            @ApiResponse(responseCode = "200", description = "Creada",
                content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Solicitud.class))),
            @ApiResponse(responseCode = "400", description = "Entrada inválida")
        }
    )
    @GetMapping
    public List<Solicitud> obtenerSolicitudes() {
        return svc.obtenerTodas();
    }

    // tecnico -- supervisor --cliente
    @Operation(
        summary = "Obtener una solicitud por ID",
        description = "Obtiene una solicitud específica según su ID",
        responses = {
            @ApiResponse(responseCode = "200", description = "Solicitud encontrada",
                content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Solicitud.class))),
            @ApiResponse(responseCode = "404", description = "Solicitud no encontrada")
        }
    )
    @GetMapping("/{id}")
    public ResponseEntity<Solicitud> obtener(@PathVariable Long id){
        return svc.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
        summary = "Crear una nueva solicitud",
        description = "Agrega una nueva solicitud al sistema",
        responses = {
            @ApiResponse(responseCode = "200", description = "Solicitud creada",
                content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Solicitud.class))),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
        }
    )

    @PostMapping
    public ResponseEntity<Solicitud> agregar(@RequestBody Solicitud s) {
        Solicitud nueva = svc.agregarSolicitud(
            s.getDescripcionProblema(),
            s.getFechaCreacion(),
            s.getFechaCierre(),
            s.getEstado(),
            s.getUsuarioId()
        );
        return ResponseEntity.ok(nueva);
    }

    //tecnico -- fecha de cierre
    @Operation(
        summary = "Actualizar una solicitud existente",
        description = "Actualiza los datos de una solicitud existente según su ID",
        responses = {
            @ApiResponse(responseCode = "200", description = "Solicitud actualizada",
                content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Solicitud.class))),
            @ApiResponse(responseCode = "404", description = "Solicitud no encontrada")
        }
    )
    @PutMapping("/{id}")
    public ResponseEntity<Solicitud> actualizar(@PathVariable Long id, @RequestBody Solicitud solicitud) {
        return svc.actualizar(id, solicitud)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // tecnico 
    @Operation(
        summary = "Eliminar una solicitud por ID",
        description = "Elimina una solicitud específica según su ID",
        responses = {
            @ApiResponse(responseCode = "204", description = "Solicitud eliminada"),
            @ApiResponse(responseCode = "404", description = "Solicitud no encontrada")
        }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        return svc.eliminar(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    //tecnico -- supervisor
    @Operation(
        summary = "Obtener solicitudes por usuario",
        description = "Obtiene todas las solicitudes asociadas a un usuario específico",
        responses = {
            @ApiResponse(responseCode = "200", description = "Solicitudes encontradas",
                content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Solicitud.class))),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
        }
    )
    @GetMapping("/usuario/{usuarioId}")
    public List<Solicitud> obtenerPorUsuario(@PathVariable Long usuarioId) {
        return svc.filtrarPorUsuario(usuarioId);
    }

    
}
