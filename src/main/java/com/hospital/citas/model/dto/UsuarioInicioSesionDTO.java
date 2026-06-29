package com.hospital.citas.model.dto;

public class UsuarioInicioSesionDTO {
    private Long id;
    private String contrasenna;
    private String nombre;
    private String primerApellido;
    private Long idRol;
    public UsuarioInicioSesionDTO() {
    }
    public UsuarioInicioSesionDTO(Long id, String contrasenna, String nombre, String primerApellido, Long idRol) {
        this.id = id;
        this.contrasenna = contrasenna;
        this.nombre = nombre;
        this.primerApellido = primerApellido;
        this.idRol = idRol;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getContrasenna() {
        return contrasenna;
    }
    public void setContrasenna(String contrasenna) {
        this.contrasenna = contrasenna;
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
    public Long getIdRol() {
        return idRol;
    }
    public void setIdRol(Long idRol) {
        this.idRol = idRol;
    }
}
