package com.hospital.citas.model.dto;

import jakarta.validation.constraints.NotBlank;

// DTO para el manejo del código OTP al momento de recuperar una contraseña.
public class CodigoResetContrasennaDTO {
    private Long idUsuario;
    @NotBlank(message = "Debe indicar el código recibido.")
    private String codigoSeguridad;

    public CodigoResetContrasennaDTO() {
    }

    public CodigoResetContrasennaDTO(Long idUsuario, String codigoSeguridad) {
        this.idUsuario = idUsuario;
        this.codigoSeguridad = codigoSeguridad;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getCodigoSeguridad() {
        return codigoSeguridad;
    }

    public void setCodigoSeguridad(String codigoSeguridad) {
        this.codigoSeguridad = codigoSeguridad;
    }
}