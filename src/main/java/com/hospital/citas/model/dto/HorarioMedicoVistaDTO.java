package com.hospital.citas.model.dto;

import java.time.LocalTime;

public class HorarioMedicoVistaDTO {
    private Long idMedico;
    private Long idDiaSemana;
    private String dia;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    public HorarioMedicoVistaDTO() {
    }
    public HorarioMedicoVistaDTO(Long idMedico, Long idDiaSemana, String dia, LocalTime horaInicio, LocalTime horaFin) {
        this.idMedico = idMedico;
        this.idDiaSemana = idDiaSemana;
        this.dia = dia;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
    }
    public Long getIdMedico() {
        return idMedico;
    }
    public void setIdMedico(Long idMedico) {
        this.idMedico = idMedico;
    }
    public Long getIdDiaSemana() {
        return idDiaSemana;
    }
    public void setIdDiaSemana(Long idDiaSemana) {
        this.idDiaSemana = idDiaSemana;
    }
    public String getDia() {
        return dia;
    }
    public void setDia(String dia) {
        this.dia = dia;
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
