package com.example.GestionSolicitudes.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.GestionSolicitudes.model.Solicitud;
import com.example.GestionSolicitudes.model.SolicitudCatalogo;
import com.example.GestionSolicitudes.model.SolicitudCatalogoId;
import com.example.GestionSolicitudes.service.SolicitudCatalogoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/solicitudes-catalogos")
public class SolicitudCatalogoController {

    private final SolicitudCatalogoService sva;

    public SolicitudCatalogoController(SolicitudCatalogoService sva) {
        this.sva = sva;
    }

    //listar las relaciones entre solicitudes y catalogos -- tecnico 
    @Operation(
        summary = "Obtener todas las solicitudes de catálogo",
        description = "Obtiene una lista de todas las solicitudes de catálogo registradas en el sistema",
        responses = {
            @ApiResponse(responseCode = "200", description = "Solicitudes obtenidas",
                content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Solicitud.class))),
            @ApiResponse(responseCode = "404", description = "No se encontraron solicitudes")
        }
    )
    @GetMapping
    public List<SolicitudCatalogo> obtenerSolicitudesCatalogos() {
        return sva.obtenerTodos();
    }

    // Busca una relación por su id compuesto -- tecnico -- administrador
    @Operation(
        summary = "Obtener una relación solicitud-catálogo por ID",
        description = "Obtiene una relación específica entre solicitud y catálogo usando su ID compuesto",
        responses = {
            @ApiResponse(responseCode = "200", description = "Relación encontrada",
                content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = SolicitudCatalogo.class))),
            @ApiResponse(responseCode = "404", description = "No se encontró la relación")
        }
    )
    @GetMapping("/{solicitudId}/{idCatalogo}")
    public ResponseEntity<SolicitudCatalogo> obtener(
            @PathVariable Long solicitudId,
            @PathVariable Long idCatalogo) {

        SolicitudCatalogoId id = new SolicitudCatalogoId(solicitudId, idCatalogo);
        return sva.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Asociar una solicitud a un catálogo -- Administrador
    @Operation(
        summary = "Asociar una solicitud a un catálogo",
        description = "Crea una nueva relación entre una solicitud y un catálogo con un subtotal",
        responses = {
            @ApiResponse(responseCode = "200", description = "Relación creada",
                content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = SolicitudCatalogo.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
        }
    )
    @PostMapping
    public ResponseEntity<SolicitudCatalogo> agregar(@RequestBody Map<String, String> body) {
        Long solicitudId = Long.parseLong(body.get("solicitudId"));
        Long catalogoId = Long.parseLong(body.get("catalogoId"));
        int subtotal = Integer.parseInt(body.get("subtotal"));

        SolicitudCatalogo sc = sva.agregarRelacion(solicitudId, catalogoId, subtotal);
        return sc != null ? ResponseEntity.ok(sc) : ResponseEntity.badRequest().build();
    }


    // Eliminar una relación por su id compuesto -- Administrador
    @Operation(
        summary = "Eliminar una relación solicitud-catálogo",
        description = "Elimina una relación específica entre solicitud y catálogo usando su ID compuesto",
        responses = {
            @ApiResponse(responseCode = "204", description = "Relación eliminada"),
            @ApiResponse(responseCode = "404", description = "No se encontró la relación")
        }
    )
    @DeleteMapping("/{solicitudId}/{idCatalogo}")
    public ResponseEntity<Void> eliminar(
            @PathVariable Long solicitudId,
            @PathVariable Long idCatalogo) {

        SolicitudCatalogoId id = new SolicitudCatalogoId(solicitudId, idCatalogo);
        try {
            sva.eliminarPorId(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}