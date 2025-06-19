package com.example.Seguimiento.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.Seguimiento.model.Seguimiento;

public interface SeguimientoRepository extends JpaRepository<Seguimiento, Long> {
     List<Seguimiento> findBySolicitudId(Long solicitudId);
}