package com.hospital.citas.model.dto;

public class PanelGestionPacienteDTO {
    private Long id;
    private String identificacion;
    private String nombreCompleto;
    private String fechaRegistro;
    private Long estado;
    public PanelGestionPacienteDTO() {
    }
    public PanelGestionPacienteDTO(Long id, String identificacion, String nombreCompleto, String fechaRegistro,
            Long estado) {
        this.id = id;
        this.identificacion = identificacion;
        this.nombreCompleto = nombreCompleto;
        this.fechaRegistro = fechaRegistro;
        this.estado = estado;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getIdentificacion() {
        return identificacion;
    }
    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }
    public String getNombreCompleto() {
        return nombreCompleto;
    }
    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }
    public String getFechaRegistro() {
        return fechaRegistro;
    }
    public void setFechaRegistro(String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
    public Long getEstado() {
        return estado;
    }
    public void setEstado(Long estado) {
        this.estado = estado;
    }
}
