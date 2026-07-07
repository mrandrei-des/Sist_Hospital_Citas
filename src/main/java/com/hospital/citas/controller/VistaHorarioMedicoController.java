package com.hospital.citas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.hospital.citas.model.dto.MedicoDTO;
import com.hospital.citas.service.HorarioMedicoService;
import jakarta.servlet.http.HttpSession;
import jakarta.websocket.server.PathParam;

import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class VistaHorarioMedicoController {

    private final HorarioMedicoService horarioMedicoService;

    VistaHorarioMedicoController(HorarioMedicoService horarioMedicoService) {
        this.horarioMedicoService = horarioMedicoService;
    }

    @GetMapping("/mostrar-horario")
    public String desplegarPaginaHorarioMedico(HttpSession session, Model model) {

        boolean esAdmin = (Long)session.getAttribute("idRolUsuarioLoggeado") == 2 ? true : false;
        String nombreCompletoUsuarioLoggeado = (String)session.getAttribute("nombreUsuarioLoggeado") + " " + (String)session.getAttribute("primerApellidoUsuarioLoggeado") + " " + (String)session.getAttribute("segundoApellidoUsuarioLoggeado");

        model.addAttribute("nombreCompletoUsuario", nombreCompletoUsuarioLoggeado);
        model.addAttribute("usuarioEsAdmin", esAdmin);
        model.addAttribute("idRolUsuario", session.getAttribute("idUsuarioLoggeado"));
        model.addAttribute("listaMedicos", horarioMedicoService.listaMedicosConHorario());
        model.addAttribute("medico", new MedicoDTO());
        return "horariosMedicos";
    }

    @GetMapping("/cargar-horario")
    //@ModelAttribute("usuario") UsuarioMiPerfilDTO usuario
    public String getMethodName(@RequestParam("id") Long idMedico, HttpSession session, Model model) {
        boolean esAdmin = (Long)session.getAttribute("idRolUsuarioLoggeado") == 2 ? true : false;
        String nombreCompletoUsuarioLoggeado = (String)session.getAttribute("nombreUsuarioLoggeado") + " " + (String)session.getAttribute("primerApellidoUsuarioLoggeado") + " " + (String)session.getAttribute("segundoApellidoUsuarioLoggeado");

        model.addAttribute("nombreCompletoUsuario", nombreCompletoUsuarioLoggeado);
        model.addAttribute("usuarioEsAdmin", esAdmin);
        model.addAttribute("idRolUsuario", session.getAttribute("idUsuarioLoggeado"));
        model.addAttribute("listaMedicos", horarioMedicoService.listaMedicosConHorario());
        model.addAttribute("medico", new MedicoDTO());
        model.addAttribute("horarioMedico", horarioMedicoService.consultarHorarioMedicoPorId(idMedico));
        return "horariosMedicos";
    }
    
}