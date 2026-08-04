package com.hospital.citas.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.hospital.citas.model.dto.CitaPacientesDTO;
import com.hospital.citas.model.dto.CitasMedicasFiltrosDTO;
import com.hospital.citas.model.dto.HistorialMedicoPacienteDTO;
import com.hospital.citas.model.dto.VistaHistorialMedicoPacienteDTO;
import com.hospital.citas.repository.ReservaCitasRepository;

@Service
public class CitaPacienteService {
    private final ReservaCitasRepository reservaCitasRepository;
    private final ConsultaDBServerService consultaDBServerService;

    CitaPacienteService(ReservaCitasRepository reservaCitasRepository, ConsultaDBServerService consultaDBServerService) {
        this.reservaCitasRepository = reservaCitasRepository;
        this.consultaDBServerService = consultaDBServerService;
    }

    public List<CitaPacientesDTO> consultaCitasPacientes() {
        return reservaCitasRepository.consultaCitasPacientes();
    }

    public List<VistaHistorialMedicoPacienteDTO> consultaHistorialPaciente(Long idUsuario) {
        LocalDateTime fechaHoraActual = consultaDBServerService.consultaFechaHoraActualServer();
        LocalDateTime fechaHoraCita;
        List<HistorialMedicoPacienteDTO> listaHistorial = reservaCitasRepository.consultaHistorialMedicoPaciente(idUsuario);
        List<VistaHistorialMedicoPacienteDTO> listaVistaHistorial = new ArrayList<>();
        VistaHistorialMedicoPacienteDTO dto;

        for (HistorialMedicoPacienteDTO lineaHistorial : listaHistorial) {
            dto = new VistaHistorialMedicoPacienteDTO();
            dto.setId(lineaHistorial.getId());
            dto.setEspecialidad(lineaHistorial.getEspecialidad());
            dto.setMedico(lineaHistorial.getMedico());
            dto.setFecha(lineaHistorial.getFecha());
            dto.setHora(lineaHistorial.getHora());
            dto.setIdEstado(lineaHistorial.getIdEstado());

            if(lineaHistorial.getIdEstado() == 7) {
                dto.setCancellable(false);
            }else {
                fechaHoraCita = LocalDateTime.of(lineaHistorial.getFecha(), lineaHistorial.getHora());
                dto.setCancellable(fechaHoraCita.isAfter(fechaHoraActual));
            }
            listaVistaHistorial.add(dto);
        }
        return listaVistaHistorial;
    }

    public List<VistaHistorialMedicoPacienteDTO> consultaHistorialPendientePaciente(Long idUsuario) {
        LocalDateTime fechaHoraActual = consultaDBServerService.consultaFechaHoraActualServer();
        LocalDateTime fechaHoraCita;
        List<HistorialMedicoPacienteDTO> listaHistorial = reservaCitasRepository.consultaHistorialMedicoPendientePaciente(idUsuario);
        List<VistaHistorialMedicoPacienteDTO> listaVistaHistorial = new ArrayList<>();
        VistaHistorialMedicoPacienteDTO dto;

        for (HistorialMedicoPacienteDTO lineaHistorial : listaHistorial) {
            dto = new VistaHistorialMedicoPacienteDTO();
            dto.setId(lineaHistorial.getId());
            dto.setEspecialidad(lineaHistorial.getEspecialidad());
            dto.setMedico(lineaHistorial.getMedico());
            dto.setFecha(lineaHistorial.getFecha());
            dto.setHora(lineaHistorial.getHora());
            dto.setIdEstado(lineaHistorial.getIdEstado());

            if(lineaHistorial.getIdEstado() == 7) {
                dto.setCancellable(false);
            }else {
                fechaHoraCita = LocalDateTime.of(lineaHistorial.getFecha(), lineaHistorial.getHora());
                dto.setCancellable(fechaHoraCita.isAfter(fechaHoraActual));
            }
            listaVistaHistorial.add(dto);
        }
        return listaVistaHistorial;
    }

    public List<CitaPacientesDTO> consultaCitasPacientesConFiltros(CitasMedicasFiltrosDTO citasFiltros) {
        return reservaCitasRepository.consultaCitasPacientesConFiltros(citasFiltros.getFiltEstado(), citasFiltros.getFiltEspecialidad(), citasFiltros.getFiltMedico(), citasFiltros.getFiltFechaInicio(), citasFiltros.getFiltFechaFin());
    }
}