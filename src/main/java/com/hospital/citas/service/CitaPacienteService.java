package com.hospital.citas.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hospital.citas.model.dto.CitaPacientesDTO;
import com.hospital.citas.model.dto.HistorialMedicoPacienteDTO;
import com.hospital.citas.repository.ReservaCitasRepository;

@Service
public class CitaPacienteService {
    private final ReservaCitasRepository reservaCitasRepository;

    CitaPacienteService(ReservaCitasRepository reservaCitasRepository) {
        this.reservaCitasRepository = reservaCitasRepository;
    }

    public List<CitaPacientesDTO> consultaCitasPacientes() {
        return reservaCitasRepository.consultaCitasPacientes();
    }

    public List<HistorialMedicoPacienteDTO> consultaHistorialPaciente(Long idUsuario) {
        return reservaCitasRepository.consultaHistorialMedicoPaciente(idUsuario);
    }

    public List<HistorialMedicoPacienteDTO> consultaHistorialPendientePaciente(Long idUsuario) {
        return reservaCitasRepository.consultaHistorialMedicoPendientePaciente(idUsuario);
    }
}
