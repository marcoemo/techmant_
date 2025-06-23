package com.example.Resenas.controller;

import com.example.Resenas.model.Resenas;
import com.example.Resenas.service.ResenasService;
import io.swagger.v3.oas.annotations.Operation;
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
    @GetMapping
    public ResponseEntity<List<Resenas>> listarResenas() {
        List<Resenas> resenas = resenasService.listarResenas();
        if (resenas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(resenas);
    }

    @Operation(summary = "Obtener reseña por su ID")
    @GetMapping("/{id}")
    public ResponseEntity<Resenas> obtenerPorId(@PathVariable Long id) {
        Optional<Resenas> resena = resenasService.buscarPorId(id);
        return resena.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Listar reseñas por ID de usuario")
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Resenas>> listarPorUsuario(@PathVariable Long usuarioId) {
        List<Resenas> resenas = resenasService.listarPorUsuario(usuarioId);
        if (resenas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(resenas);
    }

    @Operation(summary = "Crear una nueva reseña")
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
    @PutMapping("/{id}")
    public ResponseEntity<?> modificarResena(@PathVariable Long id, @RequestBody Resenas resenaActualizada) {
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
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarResena(@PathVariable Long id) {
        boolean eliminado = resenasService.eliminarPorId(id);
        if (eliminado) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}