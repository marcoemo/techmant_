package com.example.GestionSolicitudes.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.GestionSolicitudes.model.Solicitud;
import com.example.GestionSolicitudes.service.SolicitudService;

@RestController
@RequestMapping("/solicitudes")
public class SolicitudController {

    private final SolicitudService svc;

    public SolicitudController(SolicitudService svc) {
        this.svc = svc;
    }

    @GetMapping
    public List<Solicitud> obtenerSolicitudes() {
        return svc.obtenerTodas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Solicitud> obtener(@PathVariable Long id){
        return svc.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Solicitud crear(@RequestBody Solicitud solicitud) {
        return svc.crearS(solicitud);
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<Solicitud> cambiarEstado(
            @PathVariable Long id,
            @RequestParam String estado) {

        try {
            Solicitud actualizada = svc.cambiarEstado(id, estado);
            return ResponseEntity.ok(actualizada);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Solicitud> actualizar(@PathVariable Long id, @RequestBody Solicitud solicitud) {
        return svc.actualizar(id, solicitud)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        return svc.eliminar(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping("/usuario/{usuarioId}")
    public List<Solicitud> obtenerPorUsuario(@PathVariable Long usuarioId) {
        return svc.filtrarPorUsuario(usuarioId);
    }

    @GetMapping("/tecnico/{idAsignacion}")
    public List<Solicitud> obtenerPorTecnico(@PathVariable Long idAsignacion) {
        return svc.filtrarPorTecnico(idAsignacion);
    }
}
