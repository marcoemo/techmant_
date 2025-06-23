package com.example.Equipo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Equipo.model.Categoria;
import com.example.Equipo.service.CategoriaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService svc;
    
    @Operation(
        summary = "Crear una nueva categoria",
        description = "Crea una nueva categoria en el sistema",
        responses = {
            @ApiResponse(responseCode = "200", description = "Categoria creada",
                content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Categoria.class))),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
        }
    )
    @PostMapping
    public Categoria crear(@RequestBody Categoria c) {
        return svc.crear(c);
    }

    @Operation(
        summary = "Listar todas las categorias",
        description = "Obtiene una lista de todas las categorias",
        responses = {
            @ApiResponse(responseCode = "200", description = "Lista de categorias",
                content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Categoria.class)))
        }
    )
    @GetMapping
    public List<Categoria> listar() {
        return svc.obtenerTodas();
    }
    
    @Operation(
        summary = "Obtener una categoria por id",
        description = "Obtiene una categoria específica por su id",
        responses = {
            @ApiResponse(responseCode = "200", description = "Categoria encontrada",
                content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Categoria.class))),
            @ApiResponse(responseCode = "404", description = "Categoria no encontrada")
        }
    )
    @GetMapping("/{id}")
    public ResponseEntity<Categoria> obtener(@PathVariable Long id) {
        Categoria c = svc.obtenerPorId(id);
        return c != null ? ResponseEntity.ok(c) : ResponseEntity.notFound().build();
    }

    @Operation(
        summary = "Actualizar una categoria",
        description = "Actualiza una categoria existente por su id",
        responses = {
            @ApiResponse(responseCode = "200", description = "Categoria actualizada",
                content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Categoria.class))),
            @ApiResponse(responseCode = "404", description = "Categoria no encontrada")
        }
    )
    @PostMapping("/{id}")
    public ResponseEntity<Categoria> actualizar(@PathVariable Long id, @RequestBody Categoria c) {
        Categoria actualizado = svc.actualizar(id, c);
        return actualizado != null ? ResponseEntity.ok(actualizado) : ResponseEntity.notFound().build();
    }

    @Operation(
        summary = "Eliminar una categoria",
        description = "Elimina una categoria existente por su id",
        responses = {
            @ApiResponse(responseCode = "204", description = "Categoria eliminada"),
            @ApiResponse(responseCode = "404", description = "Categoria no encontrada")
        }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        svc.eliminarPorId(id);
        return ResponseEntity.noContent().build();
    }
}