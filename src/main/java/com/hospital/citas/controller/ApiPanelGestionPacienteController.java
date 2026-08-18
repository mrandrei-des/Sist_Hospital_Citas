package com.hospital.citas.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hospital.citas.model.dto.PaginacionDTO;
import com.hospital.citas.model.dto.PanelGestionPacienteDTO;
import com.hospital.citas.service.PanelGestionPacienteService;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

// API para la consulta de pacientes aplicando para la paginación
@RestController
@RequestMapping("/api/pacientes")
public class ApiPanelGestionPacienteController {
    private final PanelGestionPacienteService panelGestionPacienteService;

    ApiPanelGestionPacienteController(PanelGestionPacienteService panelGestionPacienteService) {
        this.panelGestionPacienteService = panelGestionPacienteService;
    }

    @GetMapping("/count")
    public ResponseEntity<Integer> getCantidadPacientes() {
        int cantidadUsuariosPaciente = panelGestionPacienteService.consultaCantidadUsuariosPaciente();
        if(cantidadUsuariosPaciente == 0) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cantidadUsuariosPaciente);
    }
    
    @PostMapping("/pagination")
    public ResponseEntity<List<PanelGestionPacienteDTO>> getUsuariosPacientePagination(@RequestBody PaginacionDTO paginationAttr) {
        List<PanelGestionPacienteDTO> listaUsuariosPaciente = panelGestionPacienteService.listaUsuariosPacienteDTOPagination(paginationAttr);
        if(listaUsuariosPaciente == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(listaUsuariosPaciente);
    }
}