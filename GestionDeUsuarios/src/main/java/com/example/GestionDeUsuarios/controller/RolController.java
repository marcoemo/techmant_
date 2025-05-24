package com.example.GestionDeUsuarios.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.GestionDeUsuarios.model.Rol;
import com.example.GestionDeUsuarios.service.RolService;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
@RequestMapping("/roles")
public class RolController {
    
    @Autowired
    private final RolService RS;

    public RolController(RolService RS){
        this.RS=RS;
    }

    @GetMapping()
    public ResponseEntity<List<Rol>> obtenerTodos() {
        return ResponseEntity.ok(RS.obtenerTodos());
    }
    




}
