package com.hospital.citas.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.hospital.citas.model.dto.RespuestaErrorDTO;

@RestControllerAdvice
public class GlobalExceptionHandler {
@ExceptionHandler(HorarioOcupadoException.class)
    public ResponseEntity<RespuestaErrorDTO> catchHorarioOcupado(HorarioOcupadoException ex) {
        RespuestaErrorDTO error = new RespuestaErrorDTO(HttpStatus.CONFLICT.value(), ex.getMessage(), false);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error); // HTTP 409
    }

    @ExceptionHandler(FechaCitaPasadaException.class)
    public ResponseEntity<RespuestaErrorDTO> catchFechaCitaPasada(FechaCitaPasadaException ex) {
        RespuestaErrorDTO error = new RespuestaErrorDTO(HttpStatus.NOT_ACCEPTABLE.value(), ex.getMessage(), false);
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<RespuestaErrorDTO> catchErroresGenerales(Exception ex) {
        RespuestaErrorDTO error = new RespuestaErrorDTO(500, "Error interno en el servidor. [" + ex.getMessage() + "]", false);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error); // HTTP 500
    }
}
