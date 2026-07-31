package com.hospital.citas.model.dto;

public class EspecialidadReservaDTO {
    private Long id;
    private String descripcion;
    private Long cantidad;

    public EspecialidadReservaDTO() {
    }

    public EspecialidadReservaDTO(Long id, String descripcion, Long cantidad) {
        this.id = id;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
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

    public Long getCantidad() {
        return cantidad;
    }

    public void setCantidad(Long cantidad) {
        this.cantidad = cantidad;
    }
}
