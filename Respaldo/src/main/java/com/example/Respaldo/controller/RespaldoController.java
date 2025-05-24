package com.example.Respaldo.controller;

import com.example.Respaldo.model.Respaldo;
import com.example.Respaldo.service.RespaldoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/respaldos")
public class RespaldoController {

    @Autowired
    private RespaldoService respaldoService;

    @PostMapping
    public Respaldo crearRespaldo() {
        return respaldoService.generarRespaldo();
    }
}