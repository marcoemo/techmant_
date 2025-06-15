package com.example.Resenas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Resenas.model.Resenas;

@Repository
public interface ResenaRepository extends JpaRepository<Resenas,Long> {
      // 🟣 Este es el que faltaba para filtrar por usuario
    List<Resenas> findByUsuarioId(Long usuarioId);
}
