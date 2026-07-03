package com.hospital.citas.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UsuarioInicioSesionDTO {
    private Long id;
    @NotBlank(message = "Debe indicar una contraseña.")
    private String contrasenna;
    @NotBlank(message = "Debe indicar el correo registrado.")
    @Email(message = "Debe ingresar un correo válido.")
    private String correo;
    private Long idRol;
    public UsuarioInicioSesionDTO() {
    }
    public UsuarioInicioSesionDTO(Long id, String contrasenna, String correo, Long idRol) {
        this.id = id;
        this.contrasenna = contrasenna;
        this.correo = correo;
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
    public String getCorreo() {
        return correo;
    }
    public void setCorreo(String correo) {
        this.correo = correo;
    }
    public Long getIdRol() {
        return idRol;
    }
    public void setIdRol(Long idRol) {
        this.idRol = idRol;
    }
}
