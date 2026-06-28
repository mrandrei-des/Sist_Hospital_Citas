package com.hospital.citas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.hospital.citas.model.entity.DiaDeLaSemana;

public interface DiaDeLaSemanaRepository extends JpaRepository<DiaDeLaSemana, Long> {
    
}
