package com.example.Seguimiento.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Seguimiento.model.Seguimiento;
import com.example.Seguimiento.repository.SeguimientoRepository;

@Service
public class SeguimientoService {

    @Autowired
    private SeguimientoRepository seguimientoRepository;

    public List<Seguimiento> obtenerSolis() {
        return seguimientoRepository.findAll();
    }

    public Optional<Seguimiento> obtenerId(Long id) {
        return seguimientoRepository.findById(id);
    }

    public Seguimiento guardarSeguimiento(Seguimiento seguimiento) {
        return seguimientoRepository.save(seguimiento);
    }
}
