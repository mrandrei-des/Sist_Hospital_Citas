package com.hospital.citas.service;

import java.time.LocalTime;
import java.util.List;
import org.springframework.stereotype.Service;
import com.hospital.citas.model.dto.EspecialidadDTO;
import com.hospital.citas.model.dto.HorarioMedicoDTO;
import com.hospital.citas.model.dto.HorarioMedicoVistaDTO;
import com.hospital.citas.model.entity.DiaDeLaSemana;
import com.hospital.citas.model.entity.DisponibilidadMedico;
import com.hospital.citas.model.entity.Estado;
import com.hospital.citas.model.entity.Medico;
import com.hospital.citas.repository.DisponibilidadMedicoRepository;

// Servicio que contiene los métodos necesarios para trabajar con el horario de atención del médico tanto al registrarlo como al utilizarlo.
@Service
public class DisponibilidadMedicoService {
    private final DisponibilidadMedicoRepository disponibilidadMedicoRepository;
    private final EspecialidadService especialidadService;
    private final MedicoService medicoService;

    DisponibilidadMedicoService(DisponibilidadMedicoRepository disponibilidadMedicoRepository,
            EspecialidadService especialidadService, MedicoService medicoService) {
        this.disponibilidadMedicoRepository = disponibilidadMedicoRepository;
        this.especialidadService = especialidadService;
        this.medicoService = medicoService;
    }

    // Consulta todas las especialidades que tengan médicos registrados.
    // Busca por el estado que se le pase.
    // Usado para cargar los select de filtros de especialidad para que no muestre especialidades sin médicos.
    public List<EspecialidadDTO> listaEspecialidadesConMedico(Long idEstadoEspecialidad){
        return especialidadService.listarEspecialidadesConMedicos(idEstadoEspecialidad);
    }

    // Consulta el nombre completo del médico indicado.
    // Usado al cargar el resumen de la reserva de una cita.
    public String consultaNombreMedicoPorId(Long idMedico) {
        return medicoService.consultaNombreMedicoPorId(idMedico);
    }

    // Método que se encargar de registrar el horario de atención nuevo para el médico que corresponda. A este punto ya se encuentra validado que es permitido este horario.
    // Usado al momento de registrar horarios nuevos par a los médicos.
    public boolean procesarHorarioMedico(HorarioMedicoDTO horario, Long idUsuarioLoggeado) {
        DisponibilidadMedico horarioNuevo;
        DiaDeLaSemana diaSemana;
        Medico medico = new Medico();
        medico.setId(horario.getIdMedico());
        Estado estado = new Estado();
        estado.setId(4L);
        LocalTime horaInicio = horario.getHoraInicio(), horaFin = horario.getHoraFin();
        List<Long> listIdDiasSemana = horario.getDiasSemana();
        boolean procesoExitoso = false;
        String mensajeBitacora = "El horario ha sido registrado en el sistema.";
        Long idAccion = 1L;

        if(horario.getId() != null) {
            mensajeBitacora = "El horario ha sido actualizado.";
            idAccion = 2L;
        }

        for (Long idDia : listIdDiasSemana) {
            horarioNuevo = new DisponibilidadMedico();
            diaSemana = new DiaDeLaSemana();
            diaSemana.setId(idDia);
            
            horarioNuevo.setMedico(medico);
            horarioNuevo.setDiaDeLaSemana(diaSemana);
            horarioNuevo.setHoraInicioAtencion(horaInicio);
            horarioNuevo.setHoraFinAtencion(horaFin);
            horarioNuevo.setEstado(estado);
            DisponibilidadMedico horarioRegistrado = disponibilidadMedicoRepository.save(horarioNuevo);

            if(horarioRegistrado != null) {
                disponibilidadMedicoRepository.insertaRegistroBitacoraCambios(idAccion, horarioRegistrado.getId(), mensajeBitacora, idUsuarioLoggeado);
                procesoExitoso = true;
            }
        }
        return procesoExitoso;
    }

    // Consulta los registros de horario médico que tiene el médico indicado para el día de la semana indicado.
    public List<HorarioMedicoVistaDTO> consultarHorarioMedicoPorIdDia(Long idMedico, Long idDia){
        return disponibilidadMedicoRepository.consultarHorarioMedicoPorIdDia(idMedico, idDia);
    }

    // En caso de que un médico tenga más de un horario para un mismo día, se consulta el siguiente registro de horario de atención a partir del día y hora indicada.
    // Usado para construir el horario médico que se renderiza al reservar una cita.
    public List<HorarioMedicoVistaDTO> consultarHorarioSiguientePorMedicoDiaHora(Long idMedico, Long idDia, LocalTime horaInicio){
        return disponibilidadMedicoRepository.consultarHorarioSiguientePorMedicoDiaHora(idMedico, idDia, horaInicio);
    }

    // Método general que valida que las horas de atención seleccionadas sean válidas para registrar un nuevo horario médico
    public boolean horasAtencionSonValidas(HorarioMedicoDTO horario) {
        if(!validaHorasDentroRegistro(horario)) return false;
        if(!validaHorasAfueraRegistro(horario)) return false;
        return true;
    }

    // Método que valida que las horas de atención seleccionadas al configurar un horario médico no se encuentren dentro de otro registro médico.
    private boolean validaHorasDentroRegistro(HorarioMedicoDTO horario) {
        List<Long> diasSeleccionados = horario.getDiasSemana();
        List<DisponibilidadMedico> listaRegistros;

        for (Long dia : diasSeleccionados) {
            listaRegistros = disponibilidadMedicoRepository.consultaRegistrosHorarioMedico_HorasDentroRegistro(horario.getIdMedico(), dia, horario.getHoraInicio(), horario.getHoraFin());

            if (listaRegistros.size() > 0) {
                return false;
            }
        }
        return true;
    }

    // Método que valida que las horas de atención seleccionadas al configurar un horario médico no se encuentren fuera de otro registro médico. Es decir, que las horas seleccionadas no contengan otro registro de horario en su interior.
    private boolean validaHorasAfueraRegistro(HorarioMedicoDTO horario) {
        List<Long> diasSeleccionados = horario.getDiasSemana();
        List<DisponibilidadMedico> listaRegistros;

        for (Long dia : diasSeleccionados) {
            listaRegistros = disponibilidadMedicoRepository.consultaRegistrosHorarioMedico_HorasAfueraRegistro(horario.getIdMedico(), dia, horario.getHoraInicio(), horario.getHoraFin());

            if (listaRegistros.size() > 0) {
                return false;
            }
        }
        return true;
    }
}