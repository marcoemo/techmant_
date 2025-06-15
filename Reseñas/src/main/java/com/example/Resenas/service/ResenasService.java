package com.example.Resenas.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.Resenas.model.Resenas;
import com.example.Resenas.repository.ResenaRepository;

@Service
public class ResenasService {
    
    private final ResenaRepository RP;

    public ResenasService(ResenaRepository RP){
        this.RP = RP;
    }

    public List<Resenas> listarResenas(){
        return RP.findAll();
    }

    public Resenas agregarResenas(Resenas resenas){
        return RP.save(resenas);
    }

    // 🔍 Buscar reseñas por usuario
    public List<Resenas> listarPorUsuario(Long usuarioId) {
        return RP.findByUsuarioId(usuarioId);
    }

    // 🔍 Buscar reseña por ID
    public Optional<Resenas> buscarPorId(Long id) {
        return RP.findById(id);
    }

    // ❌ Eliminar reseña por ID
    public boolean eliminarPorId(Long id) {
        if (RP.existsById(id)) {
            RP.deleteById(id);
            return true;
        }
        return false;
    }
}
