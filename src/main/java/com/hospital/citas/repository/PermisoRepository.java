package com.hospital.citas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.hospital.citas.model.entity.Permiso;

public interface PermisoRepository extends JpaRepository<Permiso, Long> {
    
}
