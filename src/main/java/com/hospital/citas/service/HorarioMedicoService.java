package com.hospital.citas.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.hospital.citas.model.dto.DiaHorarioDTO;
import com.hospital.citas.model.dto.HorarioMedicoVistaDTO;
import com.hospital.citas.model.dto.MedicoDTO;

@Service
public class HorarioMedicoService {
    private final MedicoService medicoService;
    private final DisponibilidadMedicoService disponibilidadMedicoService;

    HorarioMedicoService(MedicoService medicoService, DisponibilidadMedicoService disponibilidadMedicoService) {
        this.medicoService = medicoService;
        this.disponibilidadMedicoService = disponibilidadMedicoService;
    }

    public List<MedicoDTO> listaMedicosConHorario() {
        return medicoService.listaMedicosConHorario();
    }

    public List<DiaHorarioDTO> consultarHorarioMedicoPorId (Long id) {

        List<DiaHorarioDTO> listaDiasHorario = new ArrayList<>();
        List<HorarioMedicoVistaDTO> horarioMedico = disponibilidadMedicoService.consultarHorarioMedicoPorId(id);

        if(horarioMedico != null && horarioMedico.size() > 0) {
            LocalTime fechaInicio, fechaFin, fechaContador;
            DiaHorarioDTO diaDTO;

            // RECORRER CADA LÍNEA DEL HORARIO
            for (HorarioMedicoVistaDTO lineaHorarioMedico : horarioMedico) {

                diaDTO = new DiaHorarioDTO();
                diaDTO.setIdDiaSemana(lineaHorarioMedico.getIdDiaSemana());
                diaDTO.setNombreDia(lineaHorarioMedico.getDia());
                diaDTO.setFecha(LocalDate.now());
                diaDTO.setFechaFormateada("01-01-2001");

                fechaInicio = lineaHorarioMedico.getHoraInicio();
                fechaContador = lineaHorarioMedico.getHoraInicio();
                fechaFin = lineaHorarioMedico.getHoraFin();

                List<String> espaciosHorario = new ArrayList<>();
                String espacio;

                // CONSTRUIR LOS STRINGS DE LAS HORAS SEGÚN HORA INICIO Y HORA FIN
                while (fechaContador.isBefore(fechaFin) || fechaContador.equals(fechaFin)) {
                    espacio = fechaContador.toString();
                    espaciosHorario.add(espacio);

                    // revisar porque no está sumando a la variable
                    fechaContador = fechaContador.plusMinutes(30);
                }
                diaDTO.setListaEspacios(espaciosHorario);
                listaDiasHorario.add(diaDTO);
            }
        }
        return listaDiasHorario;
    }
}