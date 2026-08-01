package com.hospital.citas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hospital.citas.model.entity.ReservaCitas;

public interface ReservaCitasRepository extends JpaRepository<ReservaCitas, Long> {
}