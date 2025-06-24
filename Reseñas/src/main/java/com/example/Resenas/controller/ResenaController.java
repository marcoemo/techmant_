package com.example.Resenas.controller;

import com.example.Resenas.model.Resenas;
import com.example.Resenas.service.ResenasService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/resenias")
@Tag(name = "Reseñas", description = "Operaciones relacionadas con reseñas de usuarios")
public class ResenaController {

    private final ResenasService resenasService;

    public ResenaController(ResenasService resenasService) {
        this.resenasService = resenasService;
    }

    private final List<String> tiposValidos = List.of("positiva", "neutra", "negativa");

    @Operation(summary = "Listar todas las reseñas")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de reseñas obtenida correctamente"),
        @ApiResponse(responseCode = "204", description = "No hay reseñas registradas")
    })
    @GetMapping
    public ResponseEntity<List<Resenas>> listarResenas() {
        List<Resenas> resenas = resenasService.listarResenas();
        if (resenas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(resenas);
    }

    @Operation(summary = "Obtener una reseña por su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Reseña encontrada"),
        @ApiResponse(responseCode = "404", description = "Reseña no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Resenas> obtenerPorId(
        @Parameter(description = "ID de la reseña a buscar") @PathVariable Long id) {
        Optional<Resenas> resena = resenasService.buscarPorId(id);
        return resena.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Listar reseñas por ID de usuario")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Reseñas encontradas para el usuario"),
        @ApiResponse(responseCode = "204", description = "El usuario no tiene reseñas")
    })
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Resenas>> listarPorUsuario(
        @Parameter(description = "ID del usuario") @PathVariable Long usuarioId) {
        List<Resenas> resenas = resenasService.listarPorUsuario(usuarioId);
        if (resenas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(resenas);
    }

    @Operation(summary = "Crear una nueva reseña")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Reseña creada correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos para crear la reseña")
    })
    @PostMapping
    public ResponseEntity<?> crearResena(@RequestBody Resenas resena) {
        if (resena.getTipoResena() == null || !tiposValidos.contains(resena.getTipoResena().toLowerCase())) {
            return ResponseEntity.badRequest().body("El tipo de reseña debe ser: positiva, neutra o negativa");
        }

        if (resena.getUsuarioId() == null) {
            return ResponseEntity.badRequest().body("Debe especificar el ID del usuario");
        }

        Resenas creada = resenasService.agregarResena(resena);
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }

    @Operation(summary = "Modificar una reseña existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Reseña modificada correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos para modificar la reseña"),
        @ApiResponse(responseCode = "404", description = "Reseña no encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> modificarResena(
        @Parameter(description = "ID de la reseña a modificar") @PathVariable Long id,
        @RequestBody Resenas resenaActualizada) {

        if (resenaActualizada.getTipoResena() == null || !tiposValidos.contains(resenaActualizada.getTipoResena().toLowerCase())) {
            return ResponseEntity.badRequest().body("El tipo de reseña debe ser: positiva, neutra o negativa");
        }

        if (resenaActualizada.getUsuarioId() == null) {
            return ResponseEntity.badRequest().body("Debe especificar el ID del usuario");
        }

        Optional<Resenas> modificada = resenasService.modificarResena(id, resenaActualizada);
        if (modificada.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(modificada.get());
    }

    @Operation(summary = "Eliminar una reseña por su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Reseña eliminada correctamente"),
        @ApiResponse(responseCode = "404", description = "Reseña no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarResena(
        @Parameter(description = "ID de la reseña a eliminar") @PathVariable Long id) {
        boolean eliminado = resenasService.eliminarPorId(id);
        if (eliminado) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
