package com.hospital.citas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.hospital.citas.service.CitaPacienteService;
import com.hospital.citas.service.ReservaCitasService;

import jakarta.servlet.http.HttpSession;

@Controller
public class CitasMedicasController {

    private final CitaPacienteService citaPacienteService;
    private final ReservaCitasService reservaCitasService;

    CitasMedicasController(CitaPacienteService citaPacienteService, ReservaCitasService reservaCitasService) {
        this.citaPacienteService = citaPacienteService;
        this.reservaCitasService = reservaCitasService;
    }

    @GetMapping("/historial")
    public String mostrarHistorial(HttpSession session, Model model) {

        Long idUsuario = (Long)session.getAttribute("idUsuarioLoggeado");
        Long idRolUsuario = (Long)session.getAttribute("idRolUsuarioLoggeado");
        boolean esAdmin = (Long)session.getAttribute("idRolUsuarioLoggeado") == 2 ? true : false;
        String nombreCompletoUsuarioLoggeado = (String)session.getAttribute("nombreUsuarioLoggeado") + " " + (String)session.getAttribute("primerApellidoUsuarioLoggeado") + " " + (String)session.getAttribute("segundoApellidoUsuarioLoggeado");

        boolean mostrarNotificacion = (boolean)session.getAttribute("mostrarNotificacion");
        String origenNotificacion = (String)session.getAttribute("origen");

        if(mostrarNotificacion && origenNotificacion.equals("citas")) {
            model.addAttribute("mostrarNotificacion", true);
            model.addAttribute("tipoNotificacion", (String)session.getAttribute("tipoNotificacion"));
            model.addAttribute("titulo", (String)session.getAttribute("titulo"));
            model.addAttribute("detalle", (String)session.getAttribute("detalle"));

            session.setAttribute("mostrarNotificacion", false);
            session.setAttribute("origen", "");
        }

        model.addAttribute("nombreCompletoUsuario", nombreCompletoUsuarioLoggeado);
        model.addAttribute("usuarioEsAdmin", esAdmin);
        model.addAttribute("idRolUsuario", idRolUsuario);
        model.addAttribute("listaCitasPendientes", citaPacienteService.consultaHistorialPendientePaciente(idUsuario));
        model.addAttribute("listaHistorial", citaPacienteService.consultaHistorialPaciente(idUsuario));
        return "historialMedico";
    }

    @GetMapping("/citas")
    public String mostrarListadoCitas(HttpSession session, Model model) {

        Long idUsuario = (Long)session.getAttribute("idUsuarioLoggeado");
        Long idRolUsuario = (Long)session.getAttribute("idRolUsuarioLoggeado");
        boolean esAdmin = (Long)session.getAttribute("idRolUsuarioLoggeado") == 2 ? true : false;
        String nombreCompletoUsuarioLoggeado = (String)session.getAttribute("nombreUsuarioLoggeado") + " " + (String)session.getAttribute("primerApellidoUsuarioLoggeado") + " " + (String)session.getAttribute("segundoApellidoUsuarioLoggeado");

        return "citasMedicas";
    }

    @GetMapping("/citas/confirm/{idCita}")
    public String confirmarCita(@PathVariable("idCita") Long idCita, HttpSession session, Model model) {

        Long idUsuario = (Long)session.getAttribute("idUsuarioLoggeado");
        boolean resultadoConfirmacionCita = reservaCitasService.confirmarCita(idCita, idUsuario);
        session.setAttribute("mostrarNotificacion", true);
        session.setAttribute("origen", "citas");

        if(resultadoConfirmacionCita) {
            session.setAttribute("tipoNotificacion", "success");
            session.setAttribute("titulo", "¡Cita confirmada!");
            session.setAttribute("detalle", "Tu cita ha sido agendada correctamente.");
        }else {
            session.setAttribute("tipoNotificacion", "warning");
            session.setAttribute("titulo", "Cita sin confirmar.");
            session.setAttribute("detalle", "Tu cita no ha sido confirmada. Inténtelo nuevamente.");
        }
        return "redirect:/historial";
    }

    @GetMapping("/citas/cancel/{idCita}")
    public String cancelarCita(@PathVariable("idCita") Long idCita, HttpSession session, Model model) {

        Long idUsuario = (Long)session.getAttribute("idUsuarioLoggeado");
        boolean resultadoCancelacionCita = reservaCitasService.cancelarCita(idCita, idUsuario);
        session.setAttribute("mostrarNotificacion", true);
        session.setAttribute("origen", "citas");
        if(resultadoCancelacionCita) {
            session.setAttribute("tipoNotificacion", "success");
            session.setAttribute("titulo", "¡Cita cancelada!");
            session.setAttribute("detalle", "Tu cita ha sido cancelada correctamente.");
        }else {
            session.setAttribute("tipoNotificacion", "warning");
            session.setAttribute("titulo", "Cita sin cancelar.");
            session.setAttribute("detalle", "Tu cita no ha sido cancelada. Inténtelo nuevamente.");
        }
        return "redirect:/historial";
    }
}