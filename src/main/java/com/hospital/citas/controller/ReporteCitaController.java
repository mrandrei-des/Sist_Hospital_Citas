package com.hospital.citas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.hospital.citas.service.CitaPacienteService;
import com.hospital.citas.service.EspecialidadService;
import com.hospital.citas.service.EstadoService;
import com.hospital.citas.service.MedicoService;

import jakarta.servlet.http.HttpSession;

@Controller
public class ReporteCitaController {
    private final CitaPacienteService citaPacienteService;
    private final EstadoService estadoService;
    private final EspecialidadService especialidadService;
    private final MedicoService medicoService;

    ReporteCitaController(CitaPacienteService citaPacienteService, EstadoService estadoService, EspecialidadService especialidadService, MedicoService medicoService) {
        this.citaPacienteService = citaPacienteService;
        this.estadoService = estadoService;
        this.especialidadService = especialidadService;
        this.medicoService = medicoService;
    }

    @GetMapping("/citas/reporte")
    public String mostrarListadoCitas(HttpSession session, Model model) {

        Long idRolUsuario = (Long)session.getAttribute("idRolUsuarioLoggeado");
        boolean esAdmin = (Long)session.getAttribute("idRolUsuarioLoggeado") == 2 ? true : false;
        String nombreCompletoUsuarioLoggeado = (String)session.getAttribute("nombreUsuarioLoggeado") + " " + (String)session.getAttribute("primerApellidoUsuarioLoggeado") + " " + (String)session.getAttribute("segundoApellidoUsuarioLoggeado");

        if(esAdmin) {
            model.addAttribute("nombreCompletoUsuario", nombreCompletoUsuarioLoggeado);
            model.addAttribute("usuarioEsAdmin", esAdmin);
            model.addAttribute("idRolUsuario", idRolUsuario);
            model.addAttribute("listaEstados", estadoService.consultarEstadosCitas());
            model.addAttribute("listaEspecialidades", especialidadService.listaEspecialidadesConCitas());
            model.addAttribute("listaMedicos", medicoService.listaMedicosConCitas());
            model.addAttribute("listaCitas", citaPacienteService.consultaCitasPacientes(1, 5));
        }
        return "reporteCitas";
    }
}
