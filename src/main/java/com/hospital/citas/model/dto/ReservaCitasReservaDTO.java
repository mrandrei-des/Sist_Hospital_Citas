package com.hospital.citas.model.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.hospital.citas.validation.annotation.ReservaCitaValida;

// DTO con la estructura para la carga de citas médicas reservadas por los usuarios pacientes.
@ReservaCitaValida
public class ReservaCitasReservaDTO {
    private Long idEspecialidad;
    private Long idMedico;
    private Long idUsuario;
    private LocalDate fecha;
    private LocalTime hora;

    public ReservaCitasReservaDTO() {
    }

    public ReservaCitasReservaDTO(Long idEspecialidad, Long idMedico, Long idUsuario, LocalDate fecha, LocalTime hora) {
        this.idEspecialidad = idEspecialidad;
        this.idMedico = idMedico;
        this.idUsuario = idUsuario;
        this.fecha = fecha;
        this.hora = hora;
    }

    public Long getIdEspecialidad() {
        return idEspecialidad;
    }

    public void setIdEspecialidad(Long idEspecialidad) {
        this.idEspecialidad = idEspecialidad;
    }

    public Long getIdMedico() {
        return idMedico;
    }

    public void setIdMedico(Long idMedico) {
        this.idMedico = idMedico;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }
}
