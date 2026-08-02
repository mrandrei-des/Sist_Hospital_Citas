package com.hospital.citas.model.dto;

public class RespuestaErrorDTO {
    private int status;
    private String mensaje;
    private boolean procesado;
    
    public RespuestaErrorDTO() {
    }

    public RespuestaErrorDTO(int status, String mensaje, boolean procesado) {
        this.status = status;
        this.mensaje = mensaje;
        this.procesado = procesado;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public boolean isProcesado() {
        return procesado;
    }

    public void setProcesado(boolean procesado) {
        this.procesado = procesado;
    }
    
}
