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

        boolean mostrarNotificacion  = (boolean)session.getAttribute("mostrarNotificacion");
        String origenNotificacion = (String)session.getAttribute("origen");

        if(mostrarNotificacion && origenNotificacion.equals("reservaCitas")) {
            model.addAttribute("mostrarNotificacion", true);
            model.addAttribute("tipoNotificacion", (String)session.getAttribute("tipoNotificacion"));
            model.addAttribute("titulo", (String)session.getAttribute("titulo"));
            model.addAttribute("detalle", (String)session.getAttribute("detalle"));
            session.setAttribute("mostrarNotificacion", false);
            session.setAttribute("origen", "");
        }

        boolean esAdmin = (Long)session.getAttribute("idRolUsuarioLoggeado") == 2 ? true : false;
        Long idUsuarioLoggeado = (Long)session.getAttribute("idUsuarioLoggeado");
        String nombreCompletoUsuarioLoggeado = (String)session.getAttribute("nombreUsuarioLoggeado") + " " + (String)session.getAttribute("primerApellidoUsuarioLoggeado") + " " + (String)session.getAttribute("segundoApellidoUsuarioLoggeado");

        ReservaCitasReservaDTO reserva = new ReservaCitasReservaDTO();
        reserva.setIdUsuario(idUsuarioLoggeado);

        model.addAttribute("nombreCompletoUsuario", nombreCompletoUsuarioLoggeado);
        model.addAttribute("usuarioEsAdmin", esAdmin);
        model.addAttribute("idRolUsuario", session.getAttribute("idUsuarioLoggeado"));
        model.addAttribute("idUsuario", idUsuarioLoggeado);
        model.addAttribute("listaEspecialidades", especialidadService.listaEspecialidadesReserva(""));
        return "reserva";
    }

    @GetMapping("/confirmar-reserva")
    public String confirmarReserva(HttpSession session) {
        session.setAttribute("mostrarNotificacion", true);
        session.setAttribute("origen", "reservaCitas");
        session.setAttribute("tipoNotificacion", "success");
        session.setAttribute("titulo", "¡Cita reservada!");
        session.setAttribute("detalle", "La cita ha sido reservada correctamente.");
        return "redirect:/mostrar-reserva";
    }
}
