package com.example.GestionDeUsuarios.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.GestionDeUsuarios.model.Rol;
@Repository

public interface RolRepository extends JpaRepository<Rol,Long>{

}
