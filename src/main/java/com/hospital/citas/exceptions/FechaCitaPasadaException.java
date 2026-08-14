package com.hospital.citas.exceptions;

// Se crea una excepción personalizada para el control de errores para las fecha de la reserva.
public class FechaCitaPasadaException extends RuntimeException {
    public FechaCitaPasadaException(String mensajeError) {
        super(mensajeError);
    }
}