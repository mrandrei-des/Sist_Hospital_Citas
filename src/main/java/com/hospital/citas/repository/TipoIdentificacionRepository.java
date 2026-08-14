package com.hospital.citas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.hospital.citas.model.entity.TipoIdentificacion;

// Repository para la consulta de los tipos de identificación que hay en el sistema.
public interface TipoIdentificacionRepository extends JpaRepository<TipoIdentificacion, Long> {
    
}
