package com.hospital.citas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.hospital.citas.model.dto.ReservaCitasReservaDTO;
import com.hospital.citas.service.EspecialidadService;
import jakarta.servlet.http.HttpSession;

@Controller
public class ReservaController {

    private final EspecialidadService especialidadService;

    ReservaController(EspecialidadService especialidadService) {
        this.especialidadService = especialidadService;
    }

    @GetMapping("/mostrar-reserva")
    public String mostrarFormReserva(HttpSession session, Model model) {

        boolean esAdmin = (Long)session.getAttribute("idRolUsuarioLoggeado") == 2 ? true : false;
        String nombreCompletoUsuarioLoggeado = (String)session.getAttribute("nombreUsuarioLoggeado") + " " + (String)session.getAttribute("primerApellidoUsuarioLoggeado") + " " + (String)session.getAttribute("segundoApellidoUsuarioLoggeado");

        model.addAttribute("nombreCompletoUsuario", nombreCompletoUsuarioLoggeado);
        model.addAttribute("usuarioEsAdmin", esAdmin);
        model.addAttribute("idRolUsuario", session.getAttribute("idUsuarioLoggeado"));
        model.addAttribute("reserva", new ReservaCitasReservaDTO());
        model.addAttribute("listaEspecialidades", especialidadService.listaEspecialidadesReserva(""));
        return "reserva";
    }
}
