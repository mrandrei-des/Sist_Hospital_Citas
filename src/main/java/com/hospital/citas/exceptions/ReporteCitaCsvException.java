package com.hospital.citas.exceptions;

// Se crea una excepción personalizada para el control de errores en la exportación del reporte de citas a CSV.
public class ReporteCitaCsvException extends RuntimeException {
    public ReporteCitaCsvException(String mensajeError) {
        super(mensajeError);
    }
}
