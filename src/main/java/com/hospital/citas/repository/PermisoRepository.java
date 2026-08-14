package com.hospital.citas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.hospital.citas.model.entity.Permiso;

// Repository para el manejor de los permisos de los roles.
public interface PermisoRepository extends JpaRepository<Permiso, Long> {
    
}
