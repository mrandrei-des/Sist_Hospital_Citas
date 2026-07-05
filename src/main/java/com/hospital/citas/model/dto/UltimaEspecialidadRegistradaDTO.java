package com.hospital.citas.model.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class UltimaEspecialidadRegistradaDTO {
    private Long id;
    private String descripcion;
    private LocalDateTime fechaRegistro;

    public UltimaEspecialidadRegistradaDTO() {
    }

    public UltimaEspecialidadRegistradaDTO(Long id, String descripcion, LocalDateTime fechaRegistro) {
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

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }
    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getFechaFormateada() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return this.fechaRegistro.format(formatter);
    }
}
