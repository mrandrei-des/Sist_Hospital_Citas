package com.hospital.citas.exceptions;

public class HorarioOcupadoException extends RuntimeException {
    public HorarioOcupadoException(String mensajeError) {
        super(mensajeError);
    }
}
