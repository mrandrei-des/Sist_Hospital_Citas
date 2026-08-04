package com.hospital.citas.service;

import java.time.LocalDateTime;
import org.springframework.stereotype.Service;
import com.hospital.citas.repository.ConsultaDBServerRepository;

@Service
public class ConsultaDBServerService {
    private final ConsultaDBServerRepository consultaDBServerRepository;

    ConsultaDBServerService(ConsultaDBServerRepository consultaDBServerRepository) {
        this.consultaDBServerRepository = consultaDBServerRepository;
    }

    public LocalDateTime consultaFechaHoraActualServer() {
        return consultaDBServerRepository.consultaFechaHoraActualServer();
    }
}
