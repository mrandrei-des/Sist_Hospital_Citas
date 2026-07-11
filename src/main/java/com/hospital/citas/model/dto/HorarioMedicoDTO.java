package com.hospital.citas.model.dto;

import java.time.LocalTime;
import java.util.List;

import com.hospital.citas.validation.annotation.HoraInicioValida;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@HoraInicioValida
public class HorarioMedicoDTO {
    private Long id;
    private Long idMedico;

    @NotEmpty(message = "Debe seleccionar al menos un día de atención.")
    private List<Long> diasSemana;

    @NotNull(message = "Debe seleccionar la hora de inicio.")
    private LocalTime horaInicio;

    @NotNull(message = "Debe seleccionar la hora de fin.")
    private LocalTime horaFin;
    
    public HorarioMedicoDTO() {
    }
    public HorarioMedicoDTO(Long id, Long idMedico, List<Long> diasSemana, LocalTime horaInicio, LocalTime horaFin) {
        this.id = id;
        this.idMedico = idMedico;
        this.diasSemana = diasSemana;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
    }

    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdMedico() {
        return idMedico;
    }

    public void setIdMedico(Long idMedico) {
        this.idMedico = idMedico;
    }

    public List<Long> getDiasSemana() {
        return diasSemana;
    }

    public void setDiasSemana(List<Long> diasSemana) {
        this.diasSemana = diasSemana;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public LocalTime getHoraFin() {
        return horaFin;
    }
    
    public void setHoraFin(LocalTime horaFin) {
        this.horaFin = horaFin;
    }
}
