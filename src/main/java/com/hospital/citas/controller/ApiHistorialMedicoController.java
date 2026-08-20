package com.hospital.citas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hospital.citas.model.dto.PaginacionDTO;
import com.hospital.citas.model.dto.PanelGestionPacienteDTO;
import com.hospital.citas.model.dto.VistaHistorialMedicoPacienteDTO;
import com.hospital.citas.service.CitaPacienteService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/historial")
public class ApiHistorialMedicoController {

    private final CitaPacienteService citaPacienteService;

    ApiHistorialMedicoController(CitaPacienteService citaPacienteService) {
        this.citaPacienteService = citaPacienteService;
    }

    @GetMapping("/count")
    public ResponseEntity<Integer> getCantidadCitasMedicas(HttpSession session) {
        Long idUsuarioLoggeado = (Long)session.getAttribute("idUsuarioLoggeado");
        int cantidadUsuariosPaciente = citaPacienteService.consultarCantidadCitasReservadasPorPaciente(idUsuarioLoggeado);
        if(cantidadUsuariosPaciente == 0) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cantidadUsuariosPaciente);
    }
    
    @PostMapping("/pagination")
    public ResponseEntity<List<VistaHistorialMedicoPacienteDTO>> getHistorialMedicoPaginado(@RequestBody PaginacionDTO paginationAttr, HttpSession session) {
        Long idUsuarioLoggeado = (Long)session.getAttribute("idUsuarioLoggeado");
        List<VistaHistorialMedicoPacienteDTO> listaHistorialMedico = citaPacienteService.consultaHistorialPacientePaginacion(idUsuarioLoggeado, paginationAttr);
        if(listaHistorialMedico == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(listaHistorialMedico);
    }
}