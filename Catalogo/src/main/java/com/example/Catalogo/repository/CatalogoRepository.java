package com.example.Catalogo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Catalogo.model.Catalogo;

@Repository
public interface CatalogoRepository extends JpaRepository<Catalogo,Long>{

}
