package com.hospital.citas.exceptions;

public class ReporteCitaCsvException extends RuntimeException {
    public ReporteCitaCsvException(String mensajeError) {
        super(mensajeError);
    }
}
