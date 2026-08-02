package com.hospital.citas.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RestController;

import com.hospital.citas.model.dto.ReservaCitasReservaDTO;
import com.hospital.citas.service.ReservaCitasService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/reserva/citas")
public class ApiReservaCitasController {
    private final ReservaCitasService reservaCitasService;

    ApiReservaCitasController(ReservaCitasService reservaCitasService) {
        this.reservaCitasService = reservaCitasService;
    }

    @PostMapping("/reservar")
    public ResponseEntity<?> nuevaReserva(@Valid @RequestBody ReservaCitasReservaDTO reserva, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            for (ObjectError error : bindingResult.getAllErrors()) {
                errores.put(error.getCode(), error.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errores);
        }
        // GUARDAR LA RESERVA
        Map<String, ?> mensajesRespuesta = new HashMap<>();
        if (reservaCitasService.procesarReserva(reserva)) {
            mensajesRespuesta = Map.of("procesada", true, "mensaje", "Cita reservada correctamente.");
            return ResponseEntity.ok(mensajesRespuesta);
        }else {
            mensajesRespuesta = Map.of("procesada", false, "mensaje", "Cita no procesada. Inténtolo nuevamente.");
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_CONTENT).body(mensajesRespuesta);
        }
    }
}