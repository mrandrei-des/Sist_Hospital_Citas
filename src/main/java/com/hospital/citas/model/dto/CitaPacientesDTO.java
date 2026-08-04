package com.hospital.citas.model.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class CitaPacientesDTO {
    private Long id;
    private String especialidad;
    private String paciente;
    private String medico;
    private LocalDate fecha;
    private LocalTime hora;
    private Long idEstado;
    private String fechaFormateada;
    
    public CitaPacientesDTO() {
    }
    
    public CitaPacientesDTO(Long id, String especialidad, String paciente, String medico, LocalDate fecha,
            LocalTime hora, Long idEstado, String fechaFormateada) {
        this.id = id;
        this.especialidad = especialidad;
        this.paciente = paciente;
        this.medico = medico;
        this.fecha = fecha;
        this.hora = hora;
        this.idEstado = idEstado;
        this.fechaFormateada = fechaFormateada;
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

    public String getPaciente() {
        return paciente;
    }

    public void setPaciente(String paciente) {
        this.paciente = paciente;
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
    
    // public String getFechaFormateada() {
    //     DateTimeFormatter formater = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    //     return fecha.format(formater);
    // }

    public String getFechaFormateada() {
        return fechaFormateada;
    }

    public void setFechaFormateada(String fechaFormateada) {
        this.fechaFormateada = fechaFormateada;
    }
}
