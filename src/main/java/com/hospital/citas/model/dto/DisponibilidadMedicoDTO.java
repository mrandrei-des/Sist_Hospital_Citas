package com.hospital.citas.model.dto;

import java.time.LocalTime;

public class DisponibilidadMedicoDTO {
    private Long id;
    private Long idMedico;
    private Long idDia;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    public DisponibilidadMedicoDTO() {
    }
    public DisponibilidadMedicoDTO(Long id, Long idMedico, Long idDia, LocalTime horaInicio, LocalTime horaFin) {
        this.id = id;
        this.idMedico = idMedico;
        this.idDia = idDia;
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
    public Long getIdDia() {
        return idDia;
    }
    public void setIdDia(Long idDia) {
        this.idDia = idDia;
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
