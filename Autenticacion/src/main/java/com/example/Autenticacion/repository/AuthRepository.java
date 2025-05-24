package com.example.Autenticacion.repository;

import com.example.Autenticacion.model.Auth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface AuthRepository extends JpaRepository<Auth, Long> {

}