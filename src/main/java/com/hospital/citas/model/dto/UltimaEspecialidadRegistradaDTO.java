package com.hospital.citas.model.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class UltimaEspecialidadRegistradaDTO {
    private Long id;
    private String descripcion;
    private String fechaRegistro;

    public UltimaEspecialidadRegistradaDTO() {
    }

    public UltimaEspecialidadRegistradaDTO(Long id, String descripcion, String fechaRegistro) {
        this.id = id;
        this.descripcion = descripcion;
        this.fechaRegistro = fechaRegistro;
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

    public String getFechaRegistro() {
        return fechaRegistro;
    }
    public void setFechaRegistro(String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
}
