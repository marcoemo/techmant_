package com.example.Equipo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.Equipo.model.Categoria;
import com.example.Equipo.model.Equipo;
import com.example.Equipo.repository.EquipoRepository;

import jakarta.annotation.PostConstruct;

@Service
public class EquipoService {
    private final EquipoRepository equipRepo;

    public EquipoService(EquipoRepository equipRepo) {
        this.equipRepo = equipRepo;
    }
    // Crear un nuevo equipo
    public Equipo crear(Equipo e) {
        return equipRepo.save(e);
    }

    // obtener todos los equipos
    public List<Equipo> obtenerTodos() {
        return equipRepo.findAll();
    }

    // obtener un equipo por id
    public Equipo obtenerPorId(Long id) {
        return equipRepo.findById(id).orElse(null);
    }
    
    // obtener equipos por id de usuario
    public List<Equipo> buscarPorUsuario(Long usuarioId) {
        return equipRepo.findAll().stream()
                .filter(e -> e.getIdUsuario() != null && e.getIdUsuario().equals(usuarioId))
                .toList();
    }

    // Actualizar un equipo
    public Equipo actualizar(Long id, Equipo nuevo) {
        Equipo existente = equipRepo.findById(id).orElse(null);
        if (existente != null) {
            existente.setMarca(nuevo.getMarca());
            existente.setModelo(nuevo.getModelo());
            existente.setNumeroSerie(nuevo.getNumeroSerie());
            existente.setIdUsuario(nuevo.getIdUsuario());
            return equipRepo.save(existente);
        }
        return null;
    }

    //eliminar un equipo por id
    public void eliminarPorId(Long id) {
        if (equipRepo.existsById(id)) {
            equipRepo.deleteById(id);
        }
    } 

    // crear equipos iniciales
    @PostConstruct
    public void init() {
        // IDs de categoría deben coincidir con los creados en el servicio de categorías
        List<Equipo> equiposIniciales = List.of(
            crearEquipo(new Categoria(1L, "Laptops"), "Dell", "Latitude 5410", "SN-DELL-001", 4L),  
            crearEquipo(new Categoria(2L, "Impresoras"), "HP", "EliteBook 840", "SN-HP-002", 5L),      
            crearEquipo(new Categoria(3L, "celulares"), "Epson", "Samsung A54", "SN-EPSON-003", 6L)
        );
        
        crearEquiposSiNoExisten(equiposIniciales);
    }
    
    private Equipo crearEquipo(Categoria categoriaId, String marca, 
                              String modelo, String serie, Long usuarioId) {
        Equipo equipo = new Equipo();
        equipo.setCategoria(categoriaId);
        equipo.setMarca(marca);
        equipo.setModelo(modelo);
        equipo.setNumeroSerie(serie);
        equipo.setIdUsuario(usuarioId);
        return equipo;
    }
    
    private void crearEquiposSiNoExisten(List<Equipo> equipos) {
        int creados = 0;
        for (Equipo equipo : equipos) {
            if (!equipRepo.existsByNumeroSerie(equipo.getNumeroSerie())) {
                equipRepo.save(equipo);
                creados++;
                System.out.println("Equipo creado: " + equipo.getMarca() + " " + equipo.getModelo());
            }
        }
        System.out.println("Total equipos creados: " + creados + "/" + equipos.size());
    }
    
}