package com.example.Asignaciones.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Asignaciones.model.Tecnico;
import com.example.Asignaciones.service.AsignacionService;

@RestController
@RequestMapping("asignacion")

public class AsignacionController {

    @Autowired
    private AsignacionService AS;
    // Obtener todos los técnicos
    @GetMapping
    public List<Tecnico> obtenerTodos() {
        return AS.obtenerTodos();
    }
    // Obtener técnico por ID (cambiado por el web client)
    @GetMapping("/{id}")
    public ResponseEntity<Tecnico> buscarPorId(@PathVariable Long id) {
        Tecnico tecnico = AS.buscarPorId(id);
        return tecnico != null ? ResponseEntity.ok(tecnico) : ResponseEntity.notFound().build();
    }

    // Eliminar técnico por ID
    @DeleteMapping("/{id}")
    public void eliminarPorId(@PathVariable Long id) {
        AS.eliminarPorId(id);
    }

    // Modificar disponibilidad
    @PutMapping("/{id}/disponibilidad")
    public ResponseEntity<String> modificarDisponibilidad(@PathVariable Long id, @RequestBody Map<String, String> body) {
    String nuevaDisponibilidad = body.get("nuevaDisponibilidad");
    AS.modificarDisponibilidad(id, nuevaDisponibilidad);
    return ResponseEntity.ok("Tecnico modificado");
}
    @PostMapping
    public Tecnico agregarTecnico(@RequestBody Map<String, String> body) {
        String nombre = body.get("nombre");
        String disponibilidad = body.get("disponibilidad");
        return AS.agregarTecnico(nombre, disponibilidad);
    }
}