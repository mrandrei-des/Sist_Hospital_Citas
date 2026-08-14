package com.hospital.citas.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hospital.citas.model.dto.EspecialidadDTO;
import com.hospital.citas.model.dto.EspecialidadReservaDTO;
import com.hospital.citas.service.EspecialidadService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// API para el acceso a las especialidades médicas al momento de reservar una cita.
@RestController
@RequestMapping("/reserva/especialidades")
public class ApiEspecialidadController {
    private final EspecialidadService especialidadService;

    ApiEspecialidadController(EspecialidadService especialidadService) {
        this.especialidadService = especialidadService;
    }

    @GetMapping("/search/")
    public ResponseEntity<List<EspecialidadReservaDTO>> buscarEspecialidades() {
        List<EspecialidadReservaDTO> listaEspecialidades = especialidadService.listaEspecialidadesReserva("");
        if (listaEspecialidades == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(listaEspecialidades);
    }
    
    @GetMapping("/search/{filtroBusqueda}")
    public ResponseEntity<List<EspecialidadReservaDTO>> buscarEspecialidadPorDescripcion(@PathVariable String filtroBusqueda) {
        List<EspecialidadReservaDTO> listaEspecialidades = especialidadService.listaEspecialidadesReserva(filtroBusqueda);
        if (listaEspecialidades == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(listaEspecialidades);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EspecialidadDTO> buscarNombreEspecialidadPorId(@PathVariable Long id) {
        EspecialidadDTO especialidadEncontrada = especialidadService.buscarPorId(id);
        if (especialidadEncontrada == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(especialidadEncontrada);
    }
}