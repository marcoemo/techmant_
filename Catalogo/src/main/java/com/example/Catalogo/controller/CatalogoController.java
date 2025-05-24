package com.example.Catalogo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Catalogo.model.Catalogo;
import com.example.Catalogo.service.CatalogoService;

@RestController
@RequestMapping("/catalogo")
public class CatalogoController {

    private final CatalogoService CS;
    public CatalogoController(CatalogoService CS){
        this.CS=CS;
    }
    @GetMapping
    public List<Catalogo> listaCatalogo(){
        return CS.obtenerCatalogo();
    }

    // Endpoint para obtener un catálogo por su ID, Necesario para unir microservicios
    @GetMapping("/{id}")
    public ResponseEntity<Catalogo> obtenerPorId(@PathVariable Long id) {
    Catalogo c = CS.obtenerPorId(id);
    return c != null ? ResponseEntity.ok(c) : ResponseEntity.notFound().build();
    }

}
