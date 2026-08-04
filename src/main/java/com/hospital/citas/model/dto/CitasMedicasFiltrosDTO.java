package com.hospital.citas.model.dto;

import java.time.LocalDate;

public class CitasMedicasFiltrosDTO {
    private Long filtEstado;
    private Long filtEspecialidad;
    private Long filtMedico;
    private LocalDate filtFechaInicio;
    private LocalDate filtFechaFin;
    
    public CitasMedicasFiltrosDTO() {
    }
    
    public CitasMedicasFiltrosDTO(Long filtEstado, Long filtEspecialidad, Long filtMedico, LocalDate filtFechaInicio,
            LocalDate filtFechaFin) {
        this.filtEstado = filtEstado;
        this.filtEspecialidad = filtEspecialidad;
        this.filtMedico = filtMedico;
        this.filtFechaInicio = filtFechaInicio;
        this.filtFechaFin = filtFechaFin;
    }

    public Long getFiltEstado() {
        return filtEstado;
    }

    public Long getFiltEspecialidad() {
        return filtEspecialidad;
    }

    public Long getFiltMedico() {
        return filtMedico;
    }

    public LocalDate getFiltFechaInicio() {
        return filtFechaInicio;
    }

    public LocalDate getFiltFechaFin() {
        return filtFechaFin;
    }

}


//filtEstado bigint, IN filtEspecialidad bigint, IN filtMedico bigint, IN filtFechaInicio date, IN filtFechaFin DATE