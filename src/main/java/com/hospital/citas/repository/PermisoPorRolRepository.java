package com.hospital.citas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.hospital.citas.model.entity.PermisoPorRol;

public interface PermisoPorRolRepository extends JpaRepository<PermisoPorRol, Long> {
    
}
