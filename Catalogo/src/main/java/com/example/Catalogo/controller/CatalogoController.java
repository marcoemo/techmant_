package com.example.Catalogo.controller;

import com.example.Catalogo.model.Catalogo;
import com.example.Catalogo.repository.CatalogoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/catalogos")
public class CatalogoController {

    @Autowired
    private CatalogoRepository catalogoRepository;

    // 🔹 Ver todos los catálogos
    @GetMapping
    public List<Catalogo> obtenerTodos() {
        return catalogoRepository.findAll();
    }

    // 🔹 Ver uno por ID
    @GetMapping("/{id}")
    public ResponseEntity<Catalogo> obtenerPorId(@PathVariable Long id) {
        return catalogoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 🔸 Crear nuevo catálogo
    @PostMapping
    public ResponseEntity<Catalogo> crearCatalogo(@RequestBody Catalogo nuevo) {
        Catalogo guardado = catalogoRepository.save(nuevo);
        return ResponseEntity.status(201).body(guardado);
    }

    // 🔸 Editar catálogo
    @PutMapping("/{id}")
    public ResponseEntity<Catalogo> actualizarCatalogo(@PathVariable Long id, @RequestBody Catalogo actualizado) {
        Optional<Catalogo> catalogoExistente = catalogoRepository.findById(id);

        if (catalogoExistente.isPresent()) {
            Catalogo cat = catalogoExistente.get();
            cat.setNombre(actualizado.getNombre());
            cat.setDescripcion(actualizado.getDescripcion());
            cat.setPrecio(actualizado.getPrecio());
            return ResponseEntity.ok(catalogoRepository.save(cat));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 🔸 Eliminar catálogo
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCatalogo(@PathVariable Long id) {
        if (catalogoRepository.existsById(id)) {
            catalogoRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}