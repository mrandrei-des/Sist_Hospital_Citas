package com.hospital.citas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.hospital.citas.model.entity.Estado;
import com.hospital.citas.model.entity.Medico;
import com.hospital.citas.model.entity.ReservaCitas;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;


public interface ReservaCitasRepository extends JpaRepository<ReservaCitas, Long> {
    List<ReservaCitas> findAllByMedicoAndFechaAndHoraAndEstadoIn(Medico medico, LocalDate fecha, LocalTime hora, List<Estado> estado);
}