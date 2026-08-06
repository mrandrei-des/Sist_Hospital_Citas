package com.hospital.citas.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hospital.citas.model.dto.DiaHorarioDTO;
import com.hospital.citas.model.dto.DiaHorarioReservaDTO;
import com.hospital.citas.service.HorarioMedicoService;

@RestController
@RequestMapping("/reserva/horario")
public class ApiHorarioController {
    private final HorarioMedicoService horarioMedicoService;

    ApiHorarioController(HorarioMedicoService horarioMedicoService) {
        this.horarioMedicoService = horarioMedicoService;
    }

    @GetMapping("/{idUsuario}/{idMedico}")
    // public ResponseEntity<List<DiaHorarioDTO>> consultarHorarioDisponibleMedicoPorId(@PathVariable Long idUsuario, @PathVariable Long idMedico) {
    //     List<DiaHorarioDTO> horarioMedico = horarioMedicoService.consultarHorarioMedicoParaReservarAntiguo(idMedico, idUsuario);
    public ResponseEntity<List<DiaHorarioReservaDTO>> consultarHorarioDisponibleMedicoPorId(@PathVariable Long idUsuario, @PathVariable Long idMedico) {
        List<DiaHorarioReservaDTO> horarioMedico = horarioMedicoService.consultarHorarioMedicoParaReservar(idMedico, idUsuario);
        
        if (horarioMedico == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(horarioMedico);
    }

    @GetMapping("/fechas")
    public ResponseEntity<List<String>> consultarRangoFechasSemana() {

        List<String> rangoFechasSemana = horarioMedicoService.consultarRangoFechasSemana();
        if (rangoFechasSemana == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(rangoFechasSemana);
    }
}