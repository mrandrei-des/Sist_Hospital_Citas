package com.hospital.citas.model.dto;

import com.hospital.citas.validation.annotation.SoloLetras;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class MedicoDTO {
    private Long id;
    
    @NotBlank(message = "Debe indicar el nombre.")
    @SoloLetras
    private String nombre;
    
    @NotBlank(message = "Debe indicar el primer apellido.")
    @SoloLetras
    private String primerApellido;

    @NotBlank(message = "Debe indicar el segundo apellido.")
    @SoloLetras
    private String segundoApellido;

    @NotNull(message = "Debe seleccionar la especialidad.")
    private Long idEspecialidad;

    public MedicoDTO() {
    }

    public MedicoDTO(Long id, String nombre, String primerApellido, String segundoApellido, Long idEspecialidad) {
        this.id = id;
        this.nombre = nombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.idEspecialidad = idEspecialidad;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }
    
    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public Long getIdEspecialidad() {
        return idEspecialidad;
    }

    public void setIdEspecialidad(Long idEspecialidad) {
        this.idEspecialidad = idEspecialidad;
    }
}
