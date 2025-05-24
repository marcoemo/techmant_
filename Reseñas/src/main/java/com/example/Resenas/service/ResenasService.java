package com.example.Resenas.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.Resenas.model.Resenas;
import com.example.Resenas.repository.ResenaRepository;

@Service
public class ResenasService {
    
    private final ResenaRepository RP;

    public ResenasService(ResenaRepository RP){
        this.RP=RP;
    }
    
    public List<Resenas> listarResenas(){
        return RP.findAll();
    }

    public Resenas agregarResenas(Resenas resenas){
        return RP.save(resenas);
    }

}
