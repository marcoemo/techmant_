package com.example.Resenas.controller;

import com.example.Resenas.model.Resenas;
import com.example.Resenas.service.ResenasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/resenias")
public class ResenaController {

    @Autowired
    private final ResenasService RS;

    public ResenaController(ResenasService RS) {
        this.RS = RS;
    }

    // 🔹 Obtener todas las reseñas
    @GetMapping
    public ResponseEntity<List<Resenas>> listarResenas() {
        List<Resenas> resenas = RS.listarResenas();
        if (resenas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(resenas);
    }

    // 🔹 Crear nueva reseña con validaciones manuales
    @PostMapping
    public ResponseEntity<?> crearResena(@RequestBody Resenas res) {
        if (res.getResena() == null || res.getResena().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("La reseña no puede estar vacía");
        }

        if (res.getCalificacion() == null || res.getCalificacion() < 1 || res.getCalificacion() > 5) {
            return ResponseEntity.badRequest().body("La calificación debe estar entre 1 y 5");
        }

        if (res.getUsuarioId() == null) {
            return ResponseEntity.badRequest().body("Debe especificar el ID del usuario");
        }

        Resenas res2 = RS.agregarResenas(res);
        return ResponseEntity.status(HttpStatus.CREATED).body(res2);
    }

    // 🔹 Obtener reseñas por usuario
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Resenas>> obtenerPorUsuario(@PathVariable Long usuarioId) {
        List<Resenas> porUsuario = RS.listarPorUsuario(usuarioId);
        if (porUsuario.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(porUsuario);
    }

    // 🔹 Obtener una reseña por ID
    @GetMapping("/{id}")
    public ResponseEntity<Resenas> obtenerPorId(@PathVariable Long id) {
        Optional<Resenas> encontrada = RS.buscarPorId(id);
        return encontrada.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // 🔹 Eliminar reseña
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarResena(@PathVariable Long id) {
        boolean eliminada = RS.eliminarPorId(id);
        if (eliminada) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
