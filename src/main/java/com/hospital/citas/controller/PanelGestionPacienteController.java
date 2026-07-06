package com.hospital.citas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.hospital.citas.service.PanelGestionPacienteService;
import jakarta.servlet.http.HttpSession;

@Controller
public class PanelGestionPacienteController {

    private final PanelGestionPacienteService panelGestionPacienteService;

    PanelGestionPacienteController(PanelGestionPacienteService panelGestionPacienteService) {
        this.panelGestionPacienteService = panelGestionPacienteService;
    }

    @GetMapping("/mostrar-panel-pacientes")
    public String getMethodName(HttpSession session, Model model) {        
        boolean esAdmin = (Long)session.getAttribute("idRolUsuarioLoggeado") == 2 ? true : false;
        String nombreCompletoUsuarioLoggeado = (String)session.getAttribute("nombreUsuarioLoggeado") + " " + (String)session.getAttribute("primerApellidoUsuarioLoggeado") + " " + (String)session.getAttribute("segundoApellidoUsuarioLoggeado");

        model.addAttribute("usuarioEsAdmin", esAdmin);
        model.addAttribute("nombreCompletoUsuario", nombreCompletoUsuarioLoggeado);
        model.addAttribute("idRolUsuario", session.getAttribute("idUsuarioLoggeado"));
        model.addAttribute("listaPacientes", panelGestionPacienteService.listaUsuariosPacienteDTO());

        return "panelGestionPacientes";
    }
}