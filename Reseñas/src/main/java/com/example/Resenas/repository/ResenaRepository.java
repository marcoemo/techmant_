package com.example.Resenas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Resenas.model.Resenas;

@Repository
public interface ResenaRepository extends JpaRepository<Resenas,Long> {

}
