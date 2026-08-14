package com.hospital.citas.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.springframework.stereotype.Service;

import com.hospital.citas.model.dto.DiaHorarioDTO;
import com.hospital.citas.model.dto.DiaHorarioReservaDTO;
import com.hospital.citas.model.dto.EspacioHorarioDTO;
import com.hospital.citas.model.dto.HorarioMedicoVistaDTO;
import com.hospital.citas.model.dto.MedicoDTO;
import com.hospital.citas.model.entity.DiaDeLaSemana;

// Servicio dedicado a la contrucción y gestión del horario de atención médico y todos sus espacios de disponibilidad.
@Service
public class HorarioMedicoService {
    private final MedicoService medicoService;
    private final DisponibilidadMedicoService disponibilidadMedicoService;
    private final ConsultaDBServerService consultaDBServerService;
    private final DiasDeLaSemanaService diasDeLaSemanaService;
    private final ReservaCitasService reservaCitasService;

    HorarioMedicoService(MedicoService medicoService, DisponibilidadMedicoService disponibilidadMedicoService, ConsultaDBServerService consultaDBServerService, DiasDeLaSemanaService diasDeLaSemanaService, ReservaCitasService reservaCitasService) {
        this.medicoService = medicoService;
        this.disponibilidadMedicoService = disponibilidadMedicoService;
        this.consultaDBServerService = consultaDBServerService;
        this.diasDeLaSemanaService = diasDeLaSemanaService;
        this.reservaCitasService = reservaCitasService;
    }

    // Consulta los médicos que cuenten con horario de atención configurado.
    // Usado para renderizar el select de médicos en los filtros.
    public List<MedicoDTO> listaMedicosConHorario() {
        return medicoService.listaMedicosConHorario();
    }

    // Consulta todo el horario de atención del médico indicado.
    // Usado al momento de construir los espacios de atención del médico al en la reserva de citas
    public List<DiaHorarioDTO> consultarHorarioMedicoPorId (Long id) {
        LocalDateTime fechaHoraActual = consultaDBServerService.consultaFechaHoraActualServer();
        LocalDate fechaActual = fechaHoraActual.toLocalDate();
        DateTimeFormatter formater = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        // Calcular la fecha de inicio y fecha fin de la semana
        LocalDate fechaInicioSemana = calcularFechaInicioSemana(fechaActual);
        // LocalDate fechaFinSemana = calcularFechaFinSemana(fechaActual);
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

    // Devuelve la fecha formateada del primer día de la semana actual o de la semana siguiente.
    // Usado en el título de la semana al renderizar el horario de atención del médico en la reserva de citas o consulta de horarios de atención.
    public String consultarFechaInicioSemana() {
        LocalDateTime fechaHoraActual = consultaDBServerService.consultaFechaHoraActualServer();
        LocalDate fechaInicialSemana = calcularFechaInicioSemana(fechaHoraActual.toLocalDate());
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return fechaInicialSemana.format(dateTimeFormatter);
    }
    
    // Devuelve la fecha formateada del último día de la semana actual o de la semana siguiente.
    // Usado en el título de la semana al renderizar el horario de atención del médico en la reserva de citas o consulta de horarios de atención.
    public String consultarFechaFinSemana() {
        LocalDateTime fechaHoraActual = consultaDBServerService.consultaFechaHoraActualServer();
        LocalDate fechaFinalSemana = calcularFechaFinSemana(fechaHoraActual.toLocalDate());
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return fechaFinalSemana.format(dateTimeFormatter);
    }

    // Calcula y devuelve el la fecha de inicio de la semana:
    // Semana actual si el día de la consulta está entre incluyendo lunes y viernes.
    // Semana siguiente si el día de la consulta es sábado o domingo.
    private LocalDate calcularFechaInicioSemana(LocalDate fechaActual) {
        if(fechaActual.getDayOfWeek().getValue() >= 6) {
            return fechaActual.plusDays((7 - fechaActual.getDayOfWeek().getValue() + 1));

        }else {
            return fechaActual.minusDays(fechaActual.getDayOfWeek().getValue() - 1);
        }
    }

    // Calcula y devuelve el la fecha de fin de la semana:
    // Semana actual si el día de la consulta está entre incluyendo lunes y viernes.
    // Semana siguiente si el día de la consulta es sábado o domingo.
    private LocalDate calcularFechaFinSemana(LocalDate fechaActual) {
        if(fechaActual.getDayOfWeek().getValue() >= 6) {
            return fechaActual.plusDays((7 - fechaActual.getDayOfWeek().getValue() + 5));
        }else {
            return fechaActual.plusDays(5 - fechaActual.getDayOfWeek().getValue());
        }
    }

    // Consulta la fecha de inicio y fecha fin de la semana.
    // Usado en el título de la semana al renderizar el horario de atención del médico en la reserva de citas o consulta de horarios de atención.
    public List<String> consultarRangoFechasSemana() {
        List<String> rangoFechasSemana = new ArrayList<>();
        rangoFechasSemana.add(consultarFechaInicioSemana());
        rangoFechasSemana.add(consultarFechaFinSemana());
        return rangoFechasSemana;
    }

    // Método utilizado anteriormente para construir el los espacios de atención del médico indicado. 
    // Los espacios ya reservados o restringidos por horario para el paciente, eran quitados de la lista para que no se rendericen. 
    public List<DiaHorarioDTO> consultarHorarioMedicoParaReservarAntiguo (Long id, Long idUsuario) {
        LocalDateTime fechaHoraActual = consultaDBServerService.consultaFechaHoraActualServer();
        LocalTime horaActual = fechaHoraActual.toLocalTime();
        LocalDate fechaActual = fechaHoraActual.toLocalDate();
        LocalDate fechaActualFlag = fechaHoraActual.toLocalDate();
        DateTimeFormatter formater = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        Long idDiaInicioHorario = estableceDiaInicioConsultaHorario(fechaHoraActual);
        
        // Calcular la fecha de inicio y fecha fin de la semana
        LocalDate fechaInicioSemana = calcularFechaInicioSemana(fechaActual);
        LocalDate fechaDiaActual;

        //1. Consultar los días que atiende (PARA ADMIN SON TODOS, PARA PACIENTE SERÍA DEL DÍA ACTUAL EN ADELANTE PARA OMITIR DÍAS QUE YA PASARON)
        List<DiaDeLaSemana> listaDiasAtencion = diasDeLaSemanaService.consultarDiasSiguientesHorarioPorIdMedico(id, idDiaInicioHorario);

        List<DiaHorarioDTO> listaDiasHorario = new ArrayList<>();
        List<HorarioMedicoVistaDTO> horarioMedico = new ArrayList<>();

        List<String> horariosOcupadosMedico = new ArrayList<>();
        List<String> horariosRestringidosUsuario = new ArrayList<>();

        if(listaDiasAtencion != null && listaDiasAtencion.size() > 0) {
            //2. Recorrer la lista de días que atiende (ciclo)
            for (DiaDeLaSemana diaAtencion : listaDiasAtencion) {
                // Generar la fecha de ese día, usando la fecha de inicio calculada
                fechaDiaActual = fechaInicioSemana.plusDays(diaAtencion.getId() - 1);
                horaActual = fechaActualFlag.equals(fechaDiaActual) ? horaActual : LocalTime.of(0, 0, 0);

                // Las listas de horas que no deben salir al renderizar el horario del médico
                horariosOcupadosMedico = reservaCitasService.consultaHorasOcupadasPorMedico(id, fechaDiaActual);
                horariosRestringidosUsuario = reservaCitasService.consultaHoraRestrigidasPorUsuario(idUsuario, fechaDiaActual);
                
                //2.1 Por cada día que atiente, consultar los registros de horario para ese día
                // horarioMedico = disponibilidadMedicoService.consultarHorarioSiguientePorMedicoDiaHora(id, diaAtencion.getId(), horaActual);
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
                        if(horaContador.isAfter(horaActual)) {
                            espacio = horaContador.toString();
                            espaciosHorario.add(espacio);
                        }
                        horaContador = horaContador.plusMinutes(30);
                    }
                }
                // QUITAR LOS ESPACIOS YA OCUPADOS O RESTRINGIDOS PARA EL USUARIO
                if(horariosOcupadosMedico.size() > 0) espaciosHorario = eliminarEspaciosOcupados(espaciosHorario, horariosOcupadosMedico);
                if(horariosRestringidosUsuario.size() > 0) espaciosHorario = eliminarEspaciosOcupados(espaciosHorario, horariosRestringidosUsuario);

                if(espaciosHorario.size() > 0) {
                    diaDTO.setListaEspacios(espaciosHorario);
                    listaDiasHorario.add(diaDTO);
                }
            }
        }
        return listaDiasHorario;
    }

    // Método utilizado para construir el los espacios de atención del médico indicado. 
    // Cada espacio se identifica según su disponibilidad o restricción.
    public List<DiaHorarioReservaDTO> consultarHorarioMedicoParaReservar (Long id, Long idUsuario) {
        LocalDateTime fechaHoraActual = consultaDBServerService.consultaFechaHoraActualServer();
        LocalTime horaActual = fechaHoraActual.toLocalTime();
        LocalDate fechaActual = fechaHoraActual.toLocalDate();
        LocalDate fechaActualFlag = fechaHoraActual.toLocalDate();
        DateTimeFormatter formater = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        Long idDiaInicioHorario = estableceDiaInicioConsultaHorario(fechaHoraActual);
        
        // Calcular la fecha de inicio y fecha fin de la semana
        LocalDate fechaInicioSemana = calcularFechaInicioSemana(fechaActual);
        LocalDate fechaDiaActual;

        //1. Consultar los días que atiende (PARA ADMIN SON TODOS, PARA PACIENTE SERÍA DEL DÍA ACTUAL EN ADELANTE PARA OMITIR DÍAS QUE YA PASARON)
        List<DiaDeLaSemana> listaDiasAtencion = diasDeLaSemanaService.consultarDiasSiguientesHorarioPorIdMedico(id, idDiaInicioHorario);

        List<DiaHorarioReservaDTO> listaDiasHorario = new ArrayList<>();
        List<HorarioMedicoVistaDTO> horarioMedico = new ArrayList<>();

        List<String> horariosOcupadosMedico = new ArrayList<>();
        List<String> horariosRestringidosUsuario = new ArrayList<>();

        HashSet<String> listaRestriccionesMedico;
        HashSet<String> listaRestriccionesUsuario;

        if(listaDiasAtencion != null && listaDiasAtencion.size() > 0) {
            //2. Recorrer la lista de días que atiende (ciclo)
            for (DiaDeLaSemana diaAtencion : listaDiasAtencion) {
                // Generar la fecha de ese día, usando la fecha de inicio calculada
                fechaDiaActual = fechaInicioSemana.plusDays(diaAtencion.getId() - 1);
                horaActual = fechaActualFlag.equals(fechaDiaActual) ? horaActual : LocalTime.of(0, 0, 0);

                horariosOcupadosMedico = reservaCitasService.consultaHorasOcupadasPorMedico(id, fechaDiaActual);
                horariosRestringidosUsuario = reservaCitasService.consultaHoraRestrigidasPorUsuario(idUsuario, fechaDiaActual);

                listaRestriccionesMedico = horariosOcupadosMedico.size() > 0 ? devolverSetRestricciones(horariosOcupadosMedico) : new HashSet<>();
                listaRestriccionesUsuario = horariosRestringidosUsuario.size() > 0 ? devolverSetRestricciones(horariosRestringidosUsuario) : new HashSet<>();
                
                horarioMedico = disponibilidadMedicoService.consultarHorarioMedicoPorIdDia(id, diaAtencion.getId());
                DiaHorarioReservaDTO diaDTO;

                diaDTO = new DiaHorarioReservaDTO();
                diaDTO.setIdDiaSemana(diaAtencion.getId());
                diaDTO.setNombreDia(diaAtencion.getDescripcion());
                diaDTO.setFecha(fechaDiaActual);
                diaDTO.setFechaFormateada(fechaDiaActual.format(formater));

                LocalTime horaFin, horaContador;
                List<EspacioHorarioDTO> espaciosHorario = new ArrayList<>();
                EspacioHorarioDTO espacioHorario;
                String horaEspacio;

                //2.2 Recorrer la lista de registros de horario para ese día (ciclo)
                for (HorarioMedicoVistaDTO lineaHorarioMedico : horarioMedico) {
                    horaContador = lineaHorarioMedico.getHoraInicio();
                    horaFin = lineaHorarioMedico.getHoraFin();

                    //2.2.1 Crear los espacios de reserva (ciclo)
                    while (horaContador.isBefore(horaFin) || horaContador.equals(horaFin)) {
                        if(horaContador.isAfter(horaActual)) {
                            horaEspacio = horaContador.toString();
                            // EL CAMPO ESTÁ DISPONIBLE POR DEFECTO
                            espacioHorario = new EspacioHorarioDTO(true, false, horaEspacio);

                            if (listaRestriccionesMedico.size() > 0) { 
                                // SI EL MÉDICO TIENE REGISTROS YA NO ESTÁ DISPONIBLE
                                if(listaRestriccionesMedico.contains(horaEspacio)) {
                                    espacioHorario.setAvailable(false);
                                    espacioHorario.setSelected(true);
                                }
                            }

                            if(listaRestriccionesUsuario.size() > 0) {
                                if(espacioHorario.isAvailable()) {
                                    // SI EL CAMPO ESTÁ DISPONIBLE, PERO EL USUARIO TIENE UNA RESTRICCIÓN, ENTONCES PASA A NO ESTAR DISPONIBLE NI TAMPOCO SELECCIONADO, ENTONCES ES TEMA DE RESTRICCIÓN
                                    if(listaRestriccionesUsuario.contains(horaEspacio)) {
                                        espacioHorario.setAvailable(false);
                                        espacioHorario.setSelected(false);
                                    }
                                }
                            }
                            espaciosHorario.add(espacioHorario);
                        }
                        horaContador = horaContador.plusMinutes(30);
                    }
                }

                if(espaciosHorario.size() > 0) {
                    diaDTO.setListaEspacios(espaciosHorario);
                    listaDiasHorario.add(diaDTO);
                }
            }
        }
        return listaDiasHorario;
    }

    // Método que elimina de la lista los espacios que se encuentran en otra lista.
    public List<String> eliminarEspaciosOcupados(List<String> listaPorRevisar, List<String> listaReestricciones) {

        HashSet<String> reestricciones = new HashSet<String>();
        for (String reestriccion : listaReestricciones) {
            reestricciones.add(reestriccion);
        }

        List<String> listaEspaciosDisponibles = new ArrayList<>();
        for (String espacio : listaPorRevisar) {
            if(!reestricciones.contains(espacio)) listaEspaciosDisponibles.add(espacio);
        }

        return listaEspaciosDisponibles;
    }

    // Método que construye un HashSet de espacios de atención restringidos que debían ser eliminadas de la lista de espacios disponibles
    private HashSet<String> devolverSetRestricciones(List<String> listaReestricciones) {
        HashSet<String> reestricciones = new HashSet<String>();
        for (String reestriccion : listaReestricciones) {
            reestricciones.add(reestriccion);
        }

        return reestricciones;
    }

    // Método que estable el id del día de la semana desde donde debe arrancar la consulta de horarios.
    private Long estableceDiaInicioConsultaHorario(LocalDateTime fechaHoraActual) {
        int diaActual = fechaHoraActual.getDayOfWeek().getValue();
        Long diaInicio;

        if(diaActual > 5) diaInicio = 0L;
        else diaInicio = Long.valueOf((diaActual - 1));
        return diaInicio;
    }
}