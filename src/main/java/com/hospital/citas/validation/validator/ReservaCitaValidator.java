package com.hospital.citas.validation.validator;

import java.time.LocalDateTime;
import java.util.List;

import com.hospital.citas.model.dto.HorarioMedicoVistaDTO;
import com.hospital.citas.model.dto.ReservaCitasReservaDTO;
import com.hospital.citas.service.ConsultaDBServerService;
import com.hospital.citas.service.DisponibilidadMedicoService;
import com.hospital.citas.service.MedicoService;
import com.hospital.citas.service.ReservaCitasService;
import com.hospital.citas.service.UsuarioService;
import com.hospital.citas.validation.annotation.ReservaCitaValida;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ReservaCitaValidator implements ConstraintValidator<ReservaCitaValida, ReservaCitasReservaDTO> {

    private final MedicoService medicoService;
    private final UsuarioService usuarioService;
    private final ConsultaDBServerService consultaDBServerService;
    private final DisponibilidadMedicoService disponibilidadMedicoService;
    private final ReservaCitasService reservaCitasService;

    ReservaCitaValidator(MedicoService medicoService, UsuarioService usuarioService, ConsultaDBServerService consultaDBServerService, DisponibilidadMedicoService disponibilidadMedicoService, ReservaCitasService reservaCitasService) {
        this.medicoService = medicoService;
        this.usuarioService = usuarioService;
        this.consultaDBServerService = consultaDBServerService;
        this.disponibilidadMedicoService = disponibilidadMedicoService;
        this.reservaCitasService = reservaCitasService;
    }

    @Override
    public boolean isValid(ReservaCitasReservaDTO reserva, ConstraintValidatorContext context) {
        String mensajeValidacion = "";
        boolean reservaValida = true;

        if(reserva == null || (reserva.getIdMedico() == null || reserva.getIdUsuario() == null || reserva.getFecha() == null || reserva.getHora() == null)) {
            reservaValida = false;
            mensajeValidacion = "¡La cita seleccionada no es válida! Seleccione otra.";
        }else {
            LocalDateTime fechaHoraReserva = LocalDateTime.of(reserva.getFecha(), reserva.getHora());
            // VALIDACIONES
            //MÉDICO EXISTA Y ESTÉ ACTIVO
            if(medicoService.buscarPorIdYEstado(reserva.getIdMedico(), 4L) == null) {
                reservaValida = false;
                mensajeValidacion = "El médico no se encuentra disponible para atender citas.";
            }
            
            // USUARIO EXISTA Y ESTÉ ACTIVO
            if (reservaValida && usuarioService.buscarPorIdYEstado(reserva.getIdUsuario(), 4L) == null) {
                reservaValida = false;
                mensajeValidacion = "Su usuario no se encuentra disponible para reservar citas.";
            }
            
            // LA FECHA Y HORA NO HAYA TRANSCURRIDO
            if(reservaValida && consultaDBServerService.consultaFechaHoraActualServer().isAfter(fechaHoraReserva)) {
                reservaValida = false;
                mensajeValidacion = "La fecha y hora de la cita ya ha transcurrido. Seleccione otra.";
            }

            // EL DÍA Y HORA DE LA FECHA ESTÉ ENTRE LOS HORARIOS DISPONIBLES DEL MÉDICO
            if(reservaValida) {
                // traer el día de la fecha para buscar el registro de disponibilidad del médico
                int diaSemanaFecha = reserva.getFecha().getDayOfWeek().getValue();
                Long dia = Long.valueOf(diaSemanaFecha);

                List<HorarioMedicoVistaDTO> registrosHorarioDia = disponibilidadMedicoService.consultarHorarioMedicoPorIdDia(reserva.getIdMedico(), dia);
                if(registrosHorarioDia.size() == 0 ) {
                    reservaValida = false;
                    mensajeValidacion = "El médico no tiene horario registrado para el día seleccionado.";
                }else {
                    boolean horarioValido = false;
                    for (HorarioMedicoVistaDTO registroHorario : registrosHorarioDia) {
                        if((reserva.getHora().equals(registroHorario.getHoraInicio())) || (reserva.getHora().equals(registroHorario.getHoraFin()))) {
                            horarioValido = true;
                            break;
                        }else if((reserva.getHora().isAfter(registroHorario.getHoraInicio())) && (reserva.getHora().isBefore(registroHorario.getHoraFin()))) {
                            horarioValido = true;
                            break;
                        }
                    }
                    
                    if(!horarioValido) {
                        reservaValida = false;
                        mensajeValidacion = "El médico no tiene horario registrado para el día y hora seleccionado.";
                    }
                }
            }

            // LA FECHA Y HORA ESTÉN DISPONIBLES
            if (reservaValida) {
                List<Long> listaEstados = List.of(1L, 2L);
                List<ReservaCitasReservaDTO> listaReservasEncontradas = reservaCitasService.buscarCitasReservadasPorMedicoFechaHoraEstados(reserva.getIdMedico(), reserva.getFecha(), reserva.getHora(), listaEstados);
                if(listaReservasEncontradas.size() > 0) {
                    reservaValida = false;
                    mensajeValidacion = "El espacio de cita seleccionado no se encuentra disponible.";
                }
            }
        }

        if(!reservaValida) {
            context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(mensajeValidacion)
                    .addConstraintViolation();
        }
        return reservaValida;
    }
    
}
