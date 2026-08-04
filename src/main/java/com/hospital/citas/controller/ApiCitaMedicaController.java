package com.hospital.citas.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hospital.citas.model.dto.CitaPacientesDTO;
import com.hospital.citas.model.dto.CitasMedicasFiltrosDTO;
import com.hospital.citas.model.dto.MedicoReservaDTO;
import com.hospital.citas.service.CitaPacienteService;
import com.hospital.citas.service.MedicoService;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/citas")
public class ApiCitaMedicaController {

    private final MedicoService medicoService;
    private final CitaPacienteService citaPacienteService;

    ApiCitaMedicaController(MedicoService medicoService, CitaPacienteService citaPacienteService){
        this.medicoService = medicoService;
        this.citaPacienteService = citaPacienteService;
    }
    
    @GetMapping("/medicos/especialidad/{id}")
    public ResponseEntity<List<MedicoReservaDTO>> getMedicosConCitaPorEspecialidad(@PathVariable("id") Long idEspecialidad) {
        List<MedicoReservaDTO> listaMedicos = medicoService.listaMedicosConCitasPorEspecialidad(idEspecialidad);

        if(listaMedicos == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(listaMedicos);
    }

    @GetMapping("/medicos/especialidad/")
    public ResponseEntity<List<MedicoReservaDTO>> getMedicosConCita() {
        List<MedicoReservaDTO> listaMedicos = medicoService.listaMedicosConCitas();

        if(listaMedicos == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(listaMedicos);
    }

    @PostMapping("/filter")
    public ResponseEntity<List<CitaPacientesDTO>> getCitasPorFiltros(@RequestBody CitasMedicasFiltrosDTO citasFiltro) {
        List<CitaPacientesDTO> listaCitas = citaPacienteService.consultaCitasPacientesConFiltros(citasFiltro);
        if(listaCitas == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(listaCitas);
    }
}
