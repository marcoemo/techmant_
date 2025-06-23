package com.example.Resenas.service;

import java.util.List;
import java.util.Optional;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import com.example.Resenas.model.Resenas;
import com.example.Resenas.repository.ResenaRepository;

@Service
public class ResenasService implements CommandLineRunner {

    private final ResenaRepository resenaRepository;

    public ResenasService(ResenaRepository resenaRepository) {
        this.resenaRepository = resenaRepository;
    }

    // Método que corre al iniciar la aplicación para precargar datos
    @Override
    public void run(String... args) throws Exception {
        if (resenaRepository.count() == 0) {
            // Aquí puedes usar cualquier usuarioId válido para estas precargas, por ejemplo 1L
            resenaRepository.save(new Resenas(null, "positiva", 1L));
            resenaRepository.save(new Resenas(null, "neutra", 1L));
            resenaRepository.save(new Resenas(null, "negativa", 1L));
            System.out.println("Reseñas base precargadas: positiva, neutra, negativa.");
        }
    }

    // Listar todas las reseñas
    public List<Resenas> listarResenas() {
        return resenaRepository.findAll();
    }

    // Buscar reseña por idResena
    public Optional<Resenas> buscarPorId(Long idResena) {
        return resenaRepository.findById(idResena);
    }

    // Buscar todas las reseñas de un usuario
    public List<Resenas> listarPorUsuario(Long usuarioId) {
        return resenaRepository.findByUsuarioId(usuarioId);
    }

    // Crear nueva reseña
    public Resenas agregarResena(Resenas resena) {
        return resenaRepository.save(resena);
    }

    // Modificar reseña existente (si existe)
    public Optional<Resenas> modificarResena(Long idResena, Resenas resenaActualizada) {
        if (!resenaRepository.existsById(idResena)) {
            return Optional.empty(); // No existe la reseña con ese ID
        }

        return resenaRepository.findById(idResena).map(resenaExistente -> {
            resenaExistente.setTipoResena(resenaActualizada.getTipoResena());
            resenaExistente.setUsuarioId(resenaActualizada.getUsuarioId());
            return resenaRepository.save(resenaExistente);
        });
    }

    // Eliminar reseña por id
    public boolean eliminarPorId(Long idResena) {
        if (resenaRepository.existsById(idResena)) {
            resenaRepository.deleteById(idResena);
            return true;
        }
        return false;
    }
}