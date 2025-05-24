package com.example.Seguimiento.controller;

import com.example.Seguimiento.model.Seguimiento;
import com.example.Seguimiento.service.SeguimientoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/seguimientos")
public class SeguimientoController {

    private final SeguimientoService seguimientoService;

    public SeguimientoController(SeguimientoService seguimientoService) {
        this.seguimientoService = seguimientoService;
    }

    @GetMapping
    public List<Seguimiento> obtenerSolis() {
        return seguimientoService.obtenerSolis();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Seguimiento> obtener(@PathVariable Long id) {
        return seguimientoService.obtenerId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Seguimiento> crear(@RequestBody Seguimiento seguimiento) {
        Seguimiento nuevo = seguimientoService.guardarSeguimiento(seguimiento);
        return ResponseEntity.status(201).body(nuevo);
    }
}
