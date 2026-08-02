package com.hospital.citas.exceptions;

public class FechaCitaPasadaException extends RuntimeException {
    public FechaCitaPasadaException(String mensajeError) {
        super(mensajeError);
    }
}