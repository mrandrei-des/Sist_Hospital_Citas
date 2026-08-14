package com.hospital.citas.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.hospital.citas.exceptions.FechaCitaPasadaException;
import com.hospital.citas.exceptions.HorarioOcupadoException;
import com.hospital.citas.model.dto.ReservaCitasReservaDTO;
import com.hospital.citas.model.entity.Estado;
import com.hospital.citas.model.entity.Medico;
import com.hospital.citas.model.entity.ReservaCitas;
import com.hospital.citas.model.entity.Usuario;
import com.hospital.citas.repository.ReservaCitasRepository;

import jakarta.transaction.Transactional;

// Servicio encargado de la reserva de citas al igual que la consulta de espacios ya ocupados o restringidos y de la confirmación o cancelación de citas.
@Service
public class ReservaCitasService {
    private final ReservaCitasRepository reservaCitasRepository;
    private final ConsultaDBServerService consultaDBServerService;

    ReservaCitasService(ReservaCitasRepository reservaCitasRepository, ConsultaDBServerService consultaDBServerService) {
        this.reservaCitasRepository = reservaCitasRepository;
        this.consultaDBServerService = consultaDBServerService;
    }

    // Consulta las citas médicas que tengan lo o el estado indicado. Según el médico y su fecha y hora de atención.
    // Utilizado para marcar los espacios del horario de atención que ya han sido seleccionados.
    public List<ReservaCitasReservaDTO> buscarCitasReservadasPorMedicoFechaHoraEstados(Long idMedico, LocalDate fecha, LocalTime hora, List<Long> listaEstados) {
        List<ReservaCitasReservaDTO> listaReservas = new ArrayList<>();

        Medico medico = new Medico();
        medico.setId(idMedico);

        List<Estado> listaObjEstados = new ArrayList<>();
        Estado objEstado;
        for (Long estado : listaEstados) {
            objEstado = new Estado();
            objEstado.setId(estado);
            listaObjEstados.add(objEstado);
        }

        List<ReservaCitas> listaReservasEncontradas = reservaCitasRepository.findAllByMedicoAndFechaAndHoraAndEstadoIn(medico, fecha, hora, listaObjEstados);

        if(listaReservasEncontradas.size() > 0) {
            ReservaCitasReservaDTO objReserva;
            for (ReservaCitas reserva : listaReservasEncontradas) {
                objReserva = new ReservaCitasReservaDTO();
                objReserva.setIdMedico(reserva.getMedico().getId());
                objReserva.setIdUsuario(reserva.getUsuario().getId());
                objReserva.setFecha(reserva.getFecha());
                objReserva.setHora(reserva.getHora());
                listaReservas.add(objReserva);
            }
        }
        return listaReservas;
    }

    // Método que se encarga de reservar un espacio de cita médica.
    // Este método es el que permite que las reservas médicas se den.
    @Transactional
    public boolean procesarReserva(ReservaCitasReservaDTO dto) {
        List<Long> listaEstados = List.of(1L, 2L);
        List<ReservaCitasReservaDTO> listaReservasEncontradas;

        ReservaCitas objReserva = new ReservaCitas();
        String descripcionAccion  = "La cita ha sido reservada con estado de ";
        Medico medico = new Medico();
        Usuario usuario = new Usuario();
        Estado estado = new Estado();
        Long estadoObtenido = obtenerEstadoCitaPorReservar(dto.getFecha(), dto.getHora());

        if(estadoObtenido.equals(-1L)) throw new FechaCitaPasadaException("La fecha de la cita a reservar no se encuentra disponible. Seleccione otra.");
        
        descripcionAccion += estadoObtenido.equals(2L) ? "confirmada." : "pendiente.";
        medico.setId(dto.getIdMedico());
        usuario.setId(dto.getIdUsuario());
        estado.setId(estadoObtenido);
        objReserva.setMedico(medico);
        objReserva.setUsuario(usuario);
        objReserva.setFecha(dto.getFecha());
        objReserva.setHora(dto.getHora());
        objReserva.setEstado(estado);

        listaReservasEncontradas = buscarCitasReservadasPorMedicoFechaHoraEstados(dto.getIdMedico(), dto.getFecha(), dto.getHora(), listaEstados);
        if(listaReservasEncontradas.size() > 0) {
            throw new HorarioOcupadoException("El horario seleccionado ya fue reservado. No está disponible.");
        }

        ReservaCitas citaReservada = reservaCitasRepository.save(objReserva);
        if(citaReservada != null) {
            reservaCitasRepository.insertaRegistroBitacoraCambiosReservaCita(1L, citaReservada.getId(), descripcionAccion, citaReservada.getUsuario().getId());
            return true;
        }
        return false;
    }

    // Obtiene el estado de la cita que se desea reservar.
    // Si la cita a reservar está más lejos de una hora de diferencia con la fecha y hora actual se registra como pendiente.
    // Si la cita a reservar está a una hora o menos de diferencia con la fecha y hora actual se registra como confirmada.
    private Long obtenerEstadoCitaPorReservar(LocalDate fecha, LocalTime hora) {
        LocalDateTime fechaHoraCita = LocalDateTime.of(fecha, hora);
        LocalDateTime fechaHoraActual = consultaDBServerService.consultaFechaHoraActualServer();

        Long totalHoras = ChronoUnit.HOURS.between(fechaHoraActual, fechaHoraCita);

        if(totalHoras < 0) return -1L;
        if(totalHoras == 0) return 2L;
        return 1L;
    }

    // Consulta las horas de las citas médicas que ya han sido reservadas para el médico indicado en la fecha indicada.
    // Utilizado al construir los espacios de atención para el horario de atención del médico.
    public List<String> consultaHorasOcupadasPorMedico(Long idMedico, LocalDate fechaBusqueda) {
        List<String> listaHorasRestringidas = new ArrayList<>();
        List<LocalTime> listaHoras = reservaCitasRepository.consultaHorasOcupadasMedicoPorFecha(idMedico, fechaBusqueda).orElse(new ArrayList<>());

        for (LocalTime hora : listaHoras) {
            listaHorasRestringidas.add(hora.toString());
        }

        return listaHorasRestringidas;
    }

    // Consulta las horas de las citas médicas reservadas para el usuario indicado en la fecha que se le pasa.
    // Estas horas se consultan para saber cuales son los espacios que no se le deben habilitar al usuario porque generaría una duplicación de citas para el mismo día a la misma hora.
    // Utilizado al construir los espacios de atención para el horario de atención del médico.
    public List<String> consultaHoraRestrigidasPorUsuario(Long idUsuario, LocalDate fechaBusqueda) {
        List<String> listaHorasRestringidas = new ArrayList<>();
        List<LocalTime> listaHoras = reservaCitasRepository.consultaHorasRestringidasUsuarioPorFecha(idUsuario, fechaBusqueda).orElse(new ArrayList<>());

        for (LocalTime hora : listaHoras) {
            listaHorasRestringidas.add(hora.toString());
        }

        return listaHorasRestringidas;
    }

    // Consulta todas las citas médicas que el médico indicado ya tiene registradas.
    // Utilizado al cargar la tabla de citas médicas por médico seleccionado.
    public List<ReservaCitas> listaCitasEncontradasPorMedico(Long idMedico) {
        Medico medico = new Medico();
        medico.setId(idMedico);
        return reservaCitasRepository.findAllByMedico(medico);
    }

    // Método que confirma la cita médica indicada para el paciente correspondiente.
    // Utilizado en el mantenimiento de citas médica tanto del usuario paciente como del admin.
    public boolean confirmarCita(Long idCita, Long idUsuario) {
        ReservaCitas citaPorConfirmar = reservaCitasRepository.findById(idCita).orElse(null);
        if(citaPorConfirmar != null) {
            String descripcionAccion  = "La cita ha sido confirmada.";
            Estado estadoConfirmado = new Estado();
            estadoConfirmado.setId(2L);
            citaPorConfirmar.setEstado(estadoConfirmado);
            ReservaCitas citaConfirmada = reservaCitasRepository.save(citaPorConfirmar);
            if(citaConfirmada != null) {
                reservaCitasRepository.insertaRegistroBitacoraCambiosReservaCita(2L, citaConfirmada.getId(), descripcionAccion, idUsuario);
                return true;
            }
        }
        return false;
    }

    // Método que cancela la cita médica indicada para el paciente correspondiente.
    // Utilizado en el mantenimiento de citas médica tanto del usuario paciente como del admin.
    public boolean cancelarCita(Long idCita, Long idUsuario) {
        ReservaCitas citaPorCancelar = reservaCitasRepository.findById(idCita).orElse(null);
        if(citaPorCancelar != null) {
            String descripcionAccion  = "La cita ha sido cancelada.";
            Estado estadoConfirmado = new Estado();
            estadoConfirmado.setId(7L);
            citaPorCancelar.setEstado(estadoConfirmado);
            ReservaCitas citaCancelada = reservaCitasRepository.save(citaPorCancelar);
            if(citaCancelada != null) {
                reservaCitasRepository.insertaRegistroBitacoraCambiosReservaCita(2L, citaCancelada.getId(), descripcionAccion, idUsuario);
                return true;
            }
        }
        return false;
    }

    // Método que confirma de forma automática las citas médicas que se encuentran pendienes.
    // Utilizado al confirmar citas de forma automática cuando cualquier usuario inicia sesión.
    public void botConfirmarCita() {
        LocalDateTime fechaHoraActual = consultaDBServerService.consultaFechaHoraActualServer();
        fechaHoraActual = fechaHoraActual.plusHours(1);

        // Se le pasa la fecha y hora para que consulte desde ese registro hacia atrás.
        LocalDate fechaCorte = fechaHoraActual.toLocalDate();
        LocalTime horaCorte = fechaHoraActual.toLocalTime();

        List<ReservaCitas> listaCitasPorConfirmar = reservaCitasRepository.consultaCitasPendientesParaBot(fechaCorte, horaCorte).orElse(new ArrayList<>());

        if(listaCitasPorConfirmar.size() > 0) {
            ReservaCitas citaConfirmada;
            String descripcionAccion  = "La cita ha sido confirmada de forma automática por el bot.";
            Estado estadoConfirmado = new Estado();
            estadoConfirmado.setId(2L);

            for (ReservaCitas citaPorConfirmar : listaCitasPorConfirmar) {
                citaPorConfirmar.setEstado(estadoConfirmado);
                citaConfirmada = reservaCitasRepository.save(citaPorConfirmar);
                if(citaConfirmada != null) {
                    reservaCitasRepository.insertaRegistroBitacoraCambiosReservaCita(2L, citaConfirmada.getId(), descripcionAccion, 17L);
                }
            }
        }
    }
}
