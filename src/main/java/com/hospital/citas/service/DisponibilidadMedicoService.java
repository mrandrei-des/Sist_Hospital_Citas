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

    public List<EspecialidadDTO> listaEspecialidadesConMedico(Long idEstadoEspecialidad){
        return especialidadService.listarEspecialidadesConMedicos(idEstadoEspecialidad);
    }

    public String consultaNombreMedicoPorId(Long idMedico) {
        return medicoService.consultaNombreMedicoPorId(idMedico);
    }

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

    public List<HorarioMedicoVistaDTO> consultarHorarioMedicoPorIdDia(Long idMedico, Long idDia){
        return disponibilidadMedicoRepository.consultarHorarioMedicoPorIdDia(idMedico, idDia);
    }

    public boolean horasAtencionSonValidas(HorarioMedicoDTO horario) {
        if(!validaHorasDentroRegistro(horario)) return false;
        if(!validaHorasAfueraRegistro(horario)) return false;
        return true;
    }

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