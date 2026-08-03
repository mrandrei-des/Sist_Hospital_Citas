package com.hospital.citas.model.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class HistorialMedicoPacienteDTO {
    private Long id;
    private String especialidad;
    private String medico;
    private LocalDate fecha;
    private LocalTime hora;
    private Long idEstado;
    
    public HistorialMedicoPacienteDTO() {
    }
    
    public HistorialMedicoPacienteDTO(Long id, String especialidad, String medico, LocalDate fecha, LocalTime hora,
            Long idEstado) {
        this.id = id;
        this.especialidad = especialidad;
        this.medico = medico;
        this.fecha = fecha;
        this.hora = hora;
        this.idEstado = idEstado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
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

    public String getFechaFormateada() {
        DateTimeFormatter formater = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return fecha.format(formater);
    }

}
