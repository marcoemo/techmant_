package com.example.GestionSolicitudes.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.GestionSolicitudes.model.SolicitudCatalogo;
import com.example.GestionSolicitudes.model.SolicitudCatalogoId;
import com.example.GestionSolicitudes.service.SolicitudCatalogoService;

@RestController
@RequestMapping("/solicitudes-catalogos")
public class SolicitudCatalogoController {

    private final SolicitudCatalogoService sva;

    public SolicitudCatalogoController(SolicitudCatalogoService sva) {
        this.sva = sva;
    }

    //listar las relaciones entre solicitudes y catalogos
    @GetMapping
    public List<SolicitudCatalogo> obtenerSolicitudesCatalogos() {
        return sva.obtenerTodos();
    }

    //Busca una relacion por su id compuesto
    @GetMapping("/{solicitudId}/{idCatalogo}")
     public ResponseEntity<SolicitudCatalogo> obtener(
             @PathVariable Long solicitudId,
             @PathVariable Long idCatalogo) {
    
         SolicitudCatalogoId id = new SolicitudCatalogoId(solicitudId, idCatalogo);
         return sva.obtenerPorId(id)
                 .map(ResponseEntity::ok)
                 .orElse(ResponseEntity.notFound().build());
    }

    //asociar una solicitud a un catalogo
    @PostMapping
    public SolicitudCatalogo crear(@RequestBody SolicitudCatalogo relacion) {
        return sva.crearV(relacion);
    }
}
