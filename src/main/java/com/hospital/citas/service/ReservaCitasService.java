package com.hospital.citas.service;

import org.springframework.stereotype.Service;
import com.hospital.citas.repository.ReservaCitasRepository;

@Service
public class ReservaCitasService {
    private final ReservaCitasRepository reservaCitasRepository;

    ReservaCitasService(ReservaCitasRepository reservaCitasRepository) {
        this.reservaCitasRepository = reservaCitasRepository;
    }

    
}
