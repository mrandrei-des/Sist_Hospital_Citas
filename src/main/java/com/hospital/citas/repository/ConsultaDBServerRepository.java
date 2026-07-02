package com.hospital.citas.repository;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.hospital.citas.model.entity.ConsultaBDServer;

public interface ConsultaDBServerRepository extends JpaRepository<ConsultaBDServer, Long> {
    @Query(value = "SELECT NOW()", nativeQuery = true)
    LocalDateTime consultaFechaHoraActualServer();
}
