package com.hospital.citas.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.hospital.citas.model.dto.CitaPacientesDTO;
import com.hospital.citas.model.dto.CitasMedicasFiltrosDTO;
import com.hospital.citas.model.dto.HistorialMedicoPacienteDTO;
import com.hospital.citas.model.dto.PaginacionDTO;
import com.hospital.citas.model.dto.VistaHistorialMedicoPacienteDTO;
import com.hospital.citas.repository.ReservaCitasRepository;

// Servicio de consulta de información de las citas médicas de los pacientes.
@Service
public class CitaPacienteService {
    private final ReservaCitasRepository reservaCitasRepository;
    private final ConsultaDBServerService consultaDBServerService;

    CitaPacienteService(ReservaCitasRepository reservaCitasRepository, ConsultaDBServerService consultaDBServerService) {
        this.reservaCitasRepository = reservaCitasRepository;
        this.consultaDBServerService = consultaDBServerService;
    }

    // Consulta todas las citas de todos los pacientes del sistema. 
    // Está paginado por lo que recibe el número de la página a consultar y la cantidad de citas por cada página.
    // Utilizado para cargar la tabla de citas que ve el usuario admin.
    public List<CitaPacientesDTO> consultaCitasPacientes(int numPagina, int tamanoPagina) {
        return reservaCitasRepository.consultaCitasPacientes(numPagina, tamanoPagina);
    }

    // Consulta y retorna la cantidad total de citas en el sistema. 
    // No se fija en estados.
    // Usado a la hora de renderizar los botones de la paginación en la tabla de citas que ve el admin.
    public Long consultaCantidadRegistrosCitasPacientes() {
        return reservaCitasRepository.count();
    }

    // Consulta todas las citas médicas del paciente indicado.
    // No se fija en estados.
    // Utilizado para renderizar la tabla de citas que tiene el paciente en el historial médico en la carga inicial.
    public List<VistaHistorialMedicoPacienteDTO> consultaHistorialPaciente(Long idUsuario, Integer paginaCargaInicial) {
        Integer cantidadElementosPorPagina = 5;
        LocalDateTime fechaHoraActual = consultaDBServerService.consultaFechaHoraActualServer();
        LocalDateTime fechaHoraCita;

        List<HistorialMedicoPacienteDTO> listaHistorial = reservaCitasRepository.consultaHistorialMedicoPaciente(idUsuario, paginaCargaInicial, cantidadElementosPorPagina);

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

    // Consulta todas las citas médicas del paciente indicado.
    // No se fija en estados.
    // Utilizado para renderizar la tabla de citas que tiene el paciente en el historial médico en la paginación.
    public List<VistaHistorialMedicoPacienteDTO> consultaHistorialPacientePaginacion(Long idUsuario, PaginacionDTO paginacionAttr) {
        Integer cantidadElementosPorPagina = 5;
        LocalDateTime fechaHoraActual = consultaDBServerService.consultaFechaHoraActualServer();
        LocalDateTime fechaHoraCita;

        List<HistorialMedicoPacienteDTO> listaHistorial = reservaCitasRepository.consultaHistorialMedicoPaciente(idUsuario, paginacionAttr.getNumeroPagina(), 5);

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

    // Consulta todas las citas médicas pendientes del paciente indicado.
    // Solo citas pendientes.
    // Utilizado para renderizar los card pendientes del paciente en el historial médico.
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

    // Consulta las citas médicas de los pacientes aplicando filtros.
    // Los filtros pueden ser por: estado, especialidad, médico o rango de fechas; uno, varios o todos los filtros.
    // Además, cuenta con paginación por lo que recibe el número de página y cantidad de elementos a consultar.
    // Utilizado al renderizar la tabla de citas médicas que usa el usuario admin para darle mantenimiento a las citas de los pacientes.
    public List<CitaPacientesDTO> consultaCitasPacientesConFiltros(CitasMedicasFiltrosDTO citasFiltros) {
        return reservaCitasRepository.consultaCitasPacientesConFiltros(citasFiltros.getFiltEstado(), citasFiltros.getFiltEspecialidad(), citasFiltros.getFiltMedico(), citasFiltros.getFiltFechaInicio(), citasFiltros.getFiltFechaFin(), citasFiltros.getPagina(), citasFiltros.getCantidadCitasPorPagina());
    }
    
    // Consulta la cantidad de registros de las citas médicas de los pacientes aplicando filtros.
    // Los filtros pueden ser por: estado, especialidad, médico o rango de fechas; uno, varios o todos los filtros.
    // No aplica paginación, por lo que traé le total de registros.
    // Utilizado al renderizar la tabla de citas médicas que usa el usuario admin para darle mantenimiento a las citas de los pacientes.
    public int consultaCantidadCitasPacientesConFiltros(CitasMedicasFiltrosDTO citasFiltros) {
        return reservaCitasRepository.consultaCantidadCitasPacientesConFiltros(citasFiltros.getFiltEstado(), citasFiltros.getFiltEspecialidad(), citasFiltros.getFiltMedico(), citasFiltros.getFiltFechaInicio(), citasFiltros.getFiltFechaFin());
    }

    public int consultarCantidadCitasReservadasPorPaciente(Long idUsuario) {
        return reservaCitasRepository.consultaCantidadCitasReservadasPorPaciente(idUsuario);
    }
}