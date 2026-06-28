package com.hospital.citas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.hospital.citas.model.entity.Estado;

public interface EstadoRepository  extends JpaRepository<Estado, Long> {
    
}
