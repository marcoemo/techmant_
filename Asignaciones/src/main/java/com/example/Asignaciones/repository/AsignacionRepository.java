package com.example.Asignaciones.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.Asignaciones.model.Tecnico;

@Repository
public interface AsignacionRepository extends JpaRepository<Tecnico,Long>{

    @Query(value = """
    SELECT t.*, u.nombre as 'nombre del tecnico', u.id_rol as 'Rol tecnico', s.descripcion_problema
    FROM tecnico t
    JOIN usuario u ON t.usuario_id = u.id_usuario
    JOIN solicitudes s ON t.solicitud_id = s.solicitud_id
    """, nativeQuery = true)
    List<Map<String, Object>> obtenerTecnicosConUsuarioYSolicitud();
}