package com.example.Diagnosticos.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.Diagnosticos.model.Categoria;
import com.example.Diagnosticos.repository.CategoriaRepository;

@Service
public class CategoriaService {

    private final CategoriaRepository cateRepo;

    public CategoriaService(CategoriaRepository cateRepo) {
        this.cateRepo = cateRepo;
    }
    // Crear una nueva categoria
    public Categoria crear(Categoria c) {
        return cateRepo.save(c);
    }
    // obtener todas las categorias
    public List<Categoria> obtenerTodas() {
        return cateRepo.findAll();
    }
    // obtener una categoria por id
    public Categoria obtenerPorId(Long id) {
        return cateRepo.findById(id).orElse(null);
    }
    // metodo eliminar ??
}
