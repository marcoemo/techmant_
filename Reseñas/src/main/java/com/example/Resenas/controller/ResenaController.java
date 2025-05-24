package com.example.Resenas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Resenas.model.Resenas;
import com.example.Resenas.service.ResenasService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/resenias")
public class ResenaController {
    @Autowired
    private final ResenasService RS;

    public ResenaController(ResenasService RS){
        this.RS=RS;
    }



    @GetMapping
    public ResponseEntity<List<Resenas>> listarResenas(){
        //este es para guardar el resultado en una lista nueva)?(Nose si es muy necesario,pero igual)
        List<Resenas> Resenas = RS.listarResenas();
        //Si esta vacio mostrar "No Content"
        if (Resenas.isEmpty()) {
            return ResponseEntity.noContent().build();    
        }
        return ResponseEntity.ok(Resenas);
    }
       @PostMapping
       public ResponseEntity<Resenas> crearResena(@RequestBody Resenas res) {
        Resenas res2 = RS.agregarResenas(res);
        return ResponseEntity.status(HttpStatus.CREATED).body(res2);
    }

    


}
