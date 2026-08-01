package com.hospital.citas.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.hospital.citas.model.dto.MedicoReservaDTO;
import com.hospital.citas.service.MedicoService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/reserva/medicos")
public class ApiMedicosController {
    private final MedicoService medicoService;

    ApiMedicosController(MedicoService medicoService) {
        this.medicoService = medicoService;
    }

    @GetMapping("/search/{idEspecialidad}")
    public ResponseEntity<List<MedicoReservaDTO>> buscarMedicos(@PathVariable Long idEspecialidad) {

        List<MedicoReservaDTO> listaMedicos = medicoService.listaMedicosReservaPorEspecialidad(idEspecialidad, "");
        if (listaMedicos == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(listaMedicos);
    }

    @GetMapping("/search/{idEspecialidad}/{filtroBusqueda}")
    public ResponseEntity<List<MedicoReservaDTO>> buscarMedicosPorNombre(@PathVariable Long idEspecialidad, @PathVariable String filtroBusqueda) {

        List<MedicoReservaDTO> listaMedicos = medicoService.listaMedicosReservaPorEspecialidad(idEspecialidad, filtroBusqueda);
        if (listaMedicos == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(listaMedicos);
    }
}
