package com.example.Monitoreo.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.Monitoreo.model.Monitoreo;
import com.example.Monitoreo.repository.MonitoreoRepository;

@Service
public class MonitoreoService {

    private final MonitoreoRepository monre;

    public MonitoreoService(MonitoreoRepository monre) {
        this.monre = monre;
    }

    public Monitoreo registrar(Monitoreo evento) {
        evento.setFechaEvento(LocalDate.now());
        return monre.save(evento);
    }

    public List<Monitoreo> obtenerTodos() {
        return monre.findAll();
    }

    public Monitoreo obtenerPorId(Long id) {
        return monre.findById(id).orElse(null);
    }
}
