package com.example.Respaldo.controller;

import com.example.Respaldo.model.Respaldo;
import com.example.Respaldo.service.RespaldoService;

import io.swagger.v3.oas.annotations.Operation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/respaldos")
public class RespaldoController {

    @Autowired
    private RespaldoService respaldoService;
    @Operation(
        summary = "Crear un nuevo respaldo",
        description = "Genera un nuevo respaldo de la base de datos",
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Respaldo creado exitosamente"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error al crear el respaldo")
        }
    )
    @PostMapping
    public Respaldo crearRespaldo() {
        return respaldoService.generarRespaldo();
    }
}