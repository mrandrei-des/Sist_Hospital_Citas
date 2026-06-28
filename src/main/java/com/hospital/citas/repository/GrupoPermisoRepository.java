package com.hospital.citas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.hospital.citas.model.entity.GrupoPermiso;

public interface GrupoPermisoRepository extends JpaRepository<GrupoPermiso, Long> {
    
}
