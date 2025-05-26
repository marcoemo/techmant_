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

    // Obtener todas las solicitudes registradas
    @GetMapping
    public List<Solicitud> obtenerSolicitudes() {
        return svc.obtenerTodas();
    }

    // Obtener una solicitud por ID
    @GetMapping("/{id}")
    public ResponseEntity<Solicitud> obtener(@PathVariable Long id){
        return svc.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Crear una nueva solicitud
    @PostMapping
    public Solicitud crear(@RequestBody Solicitud solicitud) {
        return svc.crearS(solicitud);
    }

    // Cambiar el estado de una solicitud
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
    
}