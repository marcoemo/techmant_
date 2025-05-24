package com.example.Seguimiento.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.Seguimiento.model.Seguimiento;

public interface SeguimientoRepository extends JpaRepository<Seguimiento, Long> {
}