package com.hospital.citas.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.hospital.citas.model.dto.DiaHorarioDTO;
import com.hospital.citas.model.dto.HorarioMedicoVistaDTO;
import com.hospital.citas.model.dto.MedicoDTO;
import com.hospital.citas.model.entity.DiaDeLaSemana;

@Service
public class HorarioMedicoService {
    private final MedicoService medicoService;
    private final DisponibilidadMedicoService disponibilidadMedicoService;
    private final ConsultaDBServerService consultaDBServerService;
    private final DiasDeLaSemanaService diasDeLaSemanaService;

    HorarioMedicoService(MedicoService medicoService, DisponibilidadMedicoService disponibilidadMedicoService, ConsultaDBServerService consultaDBServerService, DiasDeLaSemanaService diasDeLaSemanaService) {
        this.medicoService = medicoService;
        this.disponibilidadMedicoService = disponibilidadMedicoService;
        this.consultaDBServerService = consultaDBServerService;
        this.diasDeLaSemanaService = diasDeLaSemanaService;
    }

    public List<MedicoDTO> listaMedicosConHorario() {
        return medicoService.listaMedicosConHorario();
    }

    public List<DiaHorarioDTO> consultarHorarioMedicoPorId (Long id) {
        LocalDateTime fechaHoraActual = consultaDBServerService.consultaFechaHoraActualServer();
        LocalDate fechaActual = fechaHoraActual.toLocalDate();
        DateTimeFormatter formater = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        // Calcular la fecha de inicio y fecha fin de la semana
        LocalDate fechaInicioSemana = calcularFechaInicioSemana(fechaActual);
        LocalDate fechaFinSemana = calcularFechaFinSemana(fechaActual);
        LocalDate fechaDiaActual;

        //1. Consultar los días que atiende (PARA ADMIN SON TODOS, PARA PACIENTE SERÍA DEL DÍA ACTUAL EN ADELANTE PARA OMITIR DÍAS QUE YA PASARON)
        List<DiaDeLaSemana> listaDiasAtencion = diasDeLaSemanaService.consultarDiasHorarioMedicoPorId(id);
        List<DiaHorarioDTO> listaDiasHorario = new ArrayList<>();
        List<HorarioMedicoVistaDTO> horarioMedico = new ArrayList<>();

        if(listaDiasAtencion != null && listaDiasAtencion.size() > 0) {
            //2. Recorrer la lista de días que atiende (ciclo)
            for (DiaDeLaSemana diaAtencion : listaDiasAtencion) {
                // Generar la fecha de ese día, usando la fecha de inicio calculada
                fechaDiaActual = fechaInicioSemana.plusDays(diaAtencion.getId() - 1);

                //2.1 Por cada día que atiente, consultar los registros de horario para ese día
                horarioMedico = disponibilidadMedicoService.consultarHorarioMedicoPorIdDia(id, diaAtencion.getId());
                DiaHorarioDTO diaDTO;

                diaDTO = new DiaHorarioDTO();
                diaDTO.setIdDiaSemana(diaAtencion.getId());
                diaDTO.setNombreDia(diaAtencion.getDescripcion());
                diaDTO.setFecha(fechaDiaActual);
                diaDTO.setFechaFormateada(fechaDiaActual.format(formater));

                LocalTime horaFin, horaContador;
                List<String> espaciosHorario = new ArrayList<>();
                String espacio;

                //2.2 Recorrer la lista de registros de horario para ese día (ciclo)
                for (HorarioMedicoVistaDTO lineaHorarioMedico : horarioMedico) {
                    horaContador = lineaHorarioMedico.getHoraInicio();
                    horaFin = lineaHorarioMedico.getHoraFin();

                    //2.2.1 Crear los espacios de reserva (ciclo)
                    while (horaContador.isBefore(horaFin) || horaContador.equals(horaFin)) {
                        espacio = horaContador.toString();
                        espaciosHorario.add(espacio);
                        horaContador = horaContador.plusMinutes(30);
                    }
                }
                diaDTO.setListaEspacios(espaciosHorario);
                listaDiasHorario.add(diaDTO);
            }
        }
        return listaDiasHorario;
    }

    public String consultarFechaInicioSemana() {
        LocalDateTime fechaHoraActual = consultaDBServerService.consultaFechaHoraActualServer();
        LocalDate fechaInicialSemana = calcularFechaInicioSemana(fechaHoraActual.toLocalDate());
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return fechaInicialSemana.format(dateTimeFormatter);
    }
    
    public String consultarFechaFinSemana() {
        LocalDateTime fechaHoraActual = consultaDBServerService.consultaFechaHoraActualServer();
        LocalDate fechaFinalSemana = calcularFechaFinSemana(fechaHoraActual.toLocalDate());
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return fechaFinalSemana.format(dateTimeFormatter);
    }

    private LocalDate calcularFechaInicioSemana(LocalDate fechaActual) {
        if(fechaActual.getDayOfWeek().getValue() >= 6) {
            return fechaActual.plusDays((7 - fechaActual.getDayOfWeek().getValue() + 1));

        }else {
            return fechaActual.minusDays(fechaActual.getDayOfWeek().getValue() - 1);
        }
    }

    private LocalDate calcularFechaFinSemana(LocalDate fechaActual) {
        if(fechaActual.getDayOfWeek().getValue() >= 6) {
            return fechaActual.plusDays((7 - fechaActual.getDayOfWeek().getValue() + 5));
        }else {
            return fechaActual.plusDays(5 - fechaActual.getDayOfWeek().getValue());
        }
    }
}