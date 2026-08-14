package com.hospital.citas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.hospital.citas.model.entity.Estado;

// Repository para la consulta de los estados de los registros.
public interface EstadoRepository  extends JpaRepository<Estado, Long> {
    List<Estado> findByIdIn(List<Integer> opcionesEstado);
}
