package com.example.Diagnosticos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.Diagnosticos.model.Diagnostico;
import com.example.Diagnosticos.service.DiagnosticoService;

@RestController
@RequestMapping("/diagnosticos")
public class DiagnosticoController {

    @Autowired
    private DiagnosticoService svc;

    @PostMapping
    public Diagnostico crear(@RequestBody Diagnostico d) {
        return svc.crear(d);
    }

    @GetMapping
    public List<Diagnostico> listar() {
        return svc.obtenerTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Diagnostico> obtener(@PathVariable Long id) {
        Diagnostico d = svc.obtenerPorId(id);
        return d != null ? ResponseEntity.ok(d) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Diagnostico> actualizar(@PathVariable Long id, @RequestBody Diagnostico d) {
        Diagnostico actualizado = svc.actualizar(id, d);
        return actualizado != null ? ResponseEntity.ok(actualizado) : ResponseEntity.notFound().build();
    }
}
