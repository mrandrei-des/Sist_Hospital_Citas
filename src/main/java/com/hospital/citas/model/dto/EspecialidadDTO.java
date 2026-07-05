package com.hospital.citas.model.dto;

import jakarta.validation.constraints.NotBlank;

public class EspecialidadDTO {
    private Long id;
    @NotBlank(message = "Debe indicar el nombre.")
    private String descripcion;
    public EspecialidadDTO() {
    }
    public EspecialidadDTO(Long id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
