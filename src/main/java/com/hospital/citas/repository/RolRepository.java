package com.hospital.citas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.hospital.citas.model.entity.Rol;

public interface RolRepository extends JpaRepository<Rol, Long> {
    
}
