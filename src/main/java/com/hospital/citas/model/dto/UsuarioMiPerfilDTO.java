package com.hospital.citas.model.dto;

import com.hospital.citas.validation.annotation.CorreoFormato;
import com.hospital.citas.validation.annotation.CorreoUnicoEdit;
import com.hospital.citas.validation.annotation.IdentificacionUnicaEdit;
import com.hospital.citas.validation.annotation.SoloLetras;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@CorreoUnicoEdit
@IdentificacionUnicaEdit
public class UsuarioMiPerfilDTO {
    private Long id;

    @NotNull(message = "Debe seleccionar el tipo de identificación")
    private Long idTipoIdentificacion;

    @NotBlank(message = "Debe indicar la identificación.")
    private String identificacion;

    private String contrasenna;
    
    @NotBlank(message = "Debe indicar el nombre.")
    @SoloLetras
    private String nombre;
    
    @NotBlank(message = "Debe indicar el primer apellido.")
    @SoloLetras
    private String primerApellido;
    
    @NotBlank(message = "Debe indicar el segundo apellido.")
    @SoloLetras
    private String segundoApellido;
    
    @NotBlank(message = "Debe indicar el correo.")
    @CorreoFormato
    private String correoElectronico;

    // @NotBlank(message = "Debe indicar el estado.")
    private Long idEstado;
    
    // @NotBlank(message = "Debe indicar el rol.")
    private Long idRol;

    public UsuarioMiPerfilDTO() {
    }

    public UsuarioMiPerfilDTO(Long id, Long idTipoIdentificacion, String identificacion, String contrasenna, String nombre, String primerApellido, String segundoApellido, String correoElectronico, Long idEstado, Long idRol) {
        this.id = id;
        this.idTipoIdentificacion = idTipoIdentificacion;
        this.identificacion = identificacion;
        this.contrasenna = contrasenna;
        this.nombre = nombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.correoElectronico = correoElectronico;
        this.idEstado = idEstado;
        this.idRol = idRol;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdTipoIdentificacion() {
        return idTipoIdentificacion;
    }

    public void setIdTipoIdentificacion(Long idTipoIdentificacion) {
        this.idTipoIdentificacion = idTipoIdentificacion;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
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

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public Long getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(Long idEstado) {
        this.idEstado = idEstado;
    }

    public Long getIdRol() {
        return idRol;
    }

    public void setIdRol(Long idRol) {
        this.idRol = idRol;
    }
}
