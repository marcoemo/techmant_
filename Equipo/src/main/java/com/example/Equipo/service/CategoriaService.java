package com.example.Equipo.service;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.Equipo.model.Categoria;
import com.example.Equipo.repository.CategoriaRepository;

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
    // Actualizar una categoria
    public Categoria actualizar(Long id, Categoria nueva) {
        Categoria existente = cateRepo.findById(id).orElse(null);
        if (existente != null) {
            existente.setNombre(nueva.getNombre());
            return cateRepo.save(existente);
        }
        return null;
    }
    //eliminar una categoria por id
    public void eliminarPorId(Long id) {
        if (cateRepo.existsById(id)) {
            cateRepo.deleteById(id);
        }
    }
}
