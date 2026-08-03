package com.hospital.citas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class CitasMedicasController {
    @GetMapping("/mostrar-historial")
    public String mostrarHistorial(HttpSession session, Model model) {

        Long idUsuario = (Long)session.getAttribute("idRolUsuarioLoggeado");
        boolean esAdmin = idUsuario == 2 ? true : false;
        String nombreCompletoUsuarioLoggeado = (String)session.getAttribute("nombreUsuarioLoggeado") + " " + (String)session.getAttribute("primerApellidoUsuarioLoggeado") + " " + (String)session.getAttribute("segundoApellidoUsuarioLoggeado");

        model.addAttribute("nombreCompletoUsuario", nombreCompletoUsuarioLoggeado);
        model.addAttribute("usuarioEsAdmin", esAdmin);
        model.addAttribute("idRolUsuario", idUsuario);
        // model.addAttribute("listaCitas", especialidadService.listarEspecialidades(4L));

        return "historialMedico";
    }

    @GetMapping("/lista-citas")
    public String mostrarListadoCitas(HttpSession session, Model model) {

        Long idUsuario = (Long)session.getAttribute("idRolUsuarioLoggeado");
        boolean esAdmin = idUsuario == 2 ? true : false;
        String nombreCompletoUsuarioLoggeado = (String)session.getAttribute("nombreUsuarioLoggeado") + " " + (String)session.getAttribute("primerApellidoUsuarioLoggeado") + " " + (String)session.getAttribute("segundoApellidoUsuarioLoggeado");

        return "citasMedicas";
    }
}
