
package com.example.Seguimiento.controller;

import com.example.Seguimiento.model.Seguimiento;
import com.example.Seguimiento.service.SeguimientoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/seguimientos")
public class SeguimientoController {

    private final SeguimientoService service;

    public SeguimientoController(SeguimientoService service) {
        this.service = service;
    }

    @GetMapping
    public List<Seguimiento> obtenerTodos() {
        return service.obtenerTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Seguimiento> obtenerPorId(@PathVariable Long id) {
        return service.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Seguimiento> crear(@RequestBody Seguimiento nuevo) {
        return ResponseEntity.status(201).body(service.crear(nuevo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Seguimiento> actualizar(@PathVariable Long id, @RequestBody Seguimiento nuevo) {
        return service.actualizar(id, nuevo)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/solicitud/{solicitudId}")
    public List<Seguimiento> porSolicitud(@PathVariable Long solicitudId) {
        return service.filtrarPorSolicitud(solicitudId);
    }
} 
