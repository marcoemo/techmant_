package com.example.GestionDeUsuarios.repository;

import com.example.GestionDeUsuarios.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByCorreo(String correo);

    
    // Verifica si ya hay un usuario con ese correo en la base de datos.
    // Esto se usa para evitar duplicados antes de crear uno nuevo.
    boolean existsByCorreo(String correo);
}