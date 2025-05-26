package com.example.Monitoreo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.Monitoreo.model.Monitoreo;
import com.example.Monitoreo.service.MonitoreoService;

@RestController
@RequestMapping("/monitoreos")
public class MonitoreoController {

    @Autowired
    private MonitoreoService svc;

    @PostMapping
    public Monitoreo crear(@RequestBody Monitoreo evento) {
        return svc.registrar(evento);
    }

    @GetMapping
    public List<Monitoreo> listar() {
        return svc.obtenerTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Monitoreo> obtener(@PathVariable Long id) {
        Monitoreo evento = svc.obtenerPorId(id);
        return evento != null ? ResponseEntity.ok(evento) : ResponseEntity.notFound().build();
    }

}
