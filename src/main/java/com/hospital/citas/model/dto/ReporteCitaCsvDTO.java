package com.hospital.citas.model.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.opencsv.bean.CsvBindByName;

public class ReporteCitaCsvDTO 
{
    @CsvBindByName(column = "ID Cita")
    private Long idCita;

    @CsvBindByName(column = "ID Especialidad")
    private Long idEspecialidad
    ;
    @CsvBindByName(column = "Especialidad")
    private String especialidad
    ;
    @CsvBindByName(column = "ID Paciente")
    private Long idPaciente;

    @CsvBindByName(column = "Paciente")
    private String paciente;

    @CsvBindByName(column = "ID Medico")
    private Long idMedico;

    @CsvBindByName(column = "Medico")
    private String medico;

    @CsvBindByName(column = "Fecha")
    private LocalDate fecha;

    @CsvBindByName(column = "Hora")
    private LocalTime hora;

    @CsvBindByName(column = "ID Estado")
    private Long idEstado;

    @CsvBindByName(column = "Estado")
    private String estado;

    public ReporteCitaCsvDTO(Long idCita, Long idEspecialidad, String especialidad, Long idPaciente, String paciente,
            Long idMedico, String medico, LocalDate fecha, LocalTime hora, Long idEstado, String estado) {
        this.idCita = idCita;
        this.idEspecialidad = idEspecialidad;
        this.especialidad = especialidad;
        this.idPaciente = idPaciente;
        this.paciente = paciente;
        this.idMedico = idMedico;
        this.medico = medico;
        this.fecha = fecha;
        this.hora = hora;
        this.idEstado = idEstado;
        this.estado = estado;
    }

    public Long getIdCita() {
        return idCita;
    }

    public void setIdCita(Long idCita) {
        this.idCita = idCita;
    }

    public Long getIdEspecialidad() {
        return idEspecialidad;
    }

    public void setIdEspecialidad(Long idEspecialidad) {
        this.idEspecialidad = idEspecialidad;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public Long getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(Long idPaciente) {
        this.idPaciente = idPaciente;
    }

    public String getPaciente() {
        return paciente;
    }

    public void setPaciente(String paciente) {
        this.paciente = paciente;
    }

    public Long getIdMedico() {
        return idMedico;
    }

    public void setIdMedico(Long idMedico) {
        this.idMedico = idMedico;
    }

    public String getMedico() {
        return medico;
    }

    public void setMedico(String medico) {
        this.medico = medico;
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

    public Long getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(Long idEstado) {
        this.idEstado = idEstado;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
