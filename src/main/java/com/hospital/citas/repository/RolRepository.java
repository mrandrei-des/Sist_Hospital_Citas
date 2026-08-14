package com.hospital.citas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.hospital.citas.model.entity.Rol;

// Repository para el manejo y consulta de los roles del sistema.
public interface RolRepository extends JpaRepository<Rol, Long> {
    
}
