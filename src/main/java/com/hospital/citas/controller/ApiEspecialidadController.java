package com.hospital.citas.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.hospital.citas.model.dto.EspecialidadReservaDTO;
import com.hospital.citas.service.EspecialidadService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/especialidades")
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
}
