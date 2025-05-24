package com.example.Diagnosticos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.Diagnosticos.model.Equipo;

@Repository
public interface EquipoRepository extends JpaRepository<Equipo, Long> {
    
}
