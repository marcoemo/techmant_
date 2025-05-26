package com.example.Reportes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.Reportes.model.Reporte;
import com.example.Reportes.service.ReporteService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/reportes")
public class ReporteController {

    @Autowired
    private ReporteService svc;

    @PostMapping
    public Reporte crear(@Valid @RequestBody Reporte r) {
        return svc.crear(r);
    }

    @GetMapping
    public List<Reporte> listar() {
        return svc.obtenerTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reporte> obtener(@PathVariable Long id) {
        Reporte r = svc.obtenerPorId(id);
        return r != null ? ResponseEntity.ok(r) : ResponseEntity.notFound().build();
    }

}
