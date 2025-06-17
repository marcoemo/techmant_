package com.example.Equipo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.Equipo.model.Equipo;

@Repository
public interface EquipoRepository extends JpaRepository<Equipo, Long> {
    
}
