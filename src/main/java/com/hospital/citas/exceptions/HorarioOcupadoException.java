package com.hospital.citas.exceptions;

// Se crea una excepción personalizada para el control de errores en caso de que el espacio de reserva seleccionado esté ocupado.
public class HorarioOcupadoException extends RuntimeException {
    public HorarioOcupadoException(String mensajeError) {
        super(mensajeError);
    }
}
