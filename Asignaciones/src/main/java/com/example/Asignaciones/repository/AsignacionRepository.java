package com.example.Asignaciones.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Asignaciones.model.Tecnico;

@Repository
public interface AsignacionRepository extends JpaRepository<Tecnico,Long>{

}
