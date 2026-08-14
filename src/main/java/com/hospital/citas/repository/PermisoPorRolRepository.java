package com.hospital.citas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.hospital.citas.model.entity.PermisoPorRol;

// Repository para la consulta y mantenimiento de los permisos por rol.
public interface PermisoPorRolRepository extends JpaRepository<PermisoPorRol, Long> {
    
}
