package com.hospital.citas.service;

import java.time.LocalDateTime;
import org.springframework.stereotype.Service;
import com.hospital.citas.repository.ConsultaDBServerRepository;

// Servicio que consulta la fecha y hora actual del servidor de base de datos.
@Service
public class ConsultaDBServerService {
    private final ConsultaDBServerRepository consultaDBServerRepository;

    ConsultaDBServerService(ConsultaDBServerRepository consultaDBServerRepository) {
        this.consultaDBServerRepository = consultaDBServerRepository;
    }

    // Consulta en la base de datos la fecha + hora actual al momento de consultar.
    // Se usa en diferentes operaciones de fecha o validaciones de horarios médicos.
    public LocalDateTime consultaFechaHoraActualServer() {
        return consultaDBServerRepository.consultaFechaHoraActualServer();
    }
}
