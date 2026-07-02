package com.hospital.citas.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hospital.citas.repository.ConsultaDBServerRepository;

@Service
public class ConsultaDBServerService {
    @Autowired
    private ConsultaDBServerRepository consultaDBServerRepository;

    public LocalDateTime consultaFechaHoraActualServer() {
        return consultaDBServerRepository.consultaFechaHoraActualServer();
    }
}
