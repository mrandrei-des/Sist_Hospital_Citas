package com.hospital.citas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.hospital.citas.service.CitaPacienteService;
import com.hospital.citas.service.EspecialidadService;
import com.hospital.citas.service.EstadoService;
import com.hospital.citas.service.MedicoService;
import com.hospital.citas.service.ReservaCitasService;

import jakarta.servlet.http.HttpSession;

// Controlador para al consulta de citas médicas y también para la confirmación y cancelación de estas.
@Controller
public class CitasMedicasController {

    private final CitaPacienteService citaPacienteService;
    private final ReservaCitasService reservaCitasService;
    private final EstadoService estadoService;
    private final EspecialidadService especialidadService;
    private final MedicoService medicoService;

    CitasMedicasController(CitaPacienteService citaPacienteService, ReservaCitasService reservaCitasService, EstadoService estadoService, EspecialidadService especialidadService, MedicoService medicoService) {
        this.citaPacienteService = citaPacienteService;
        this.reservaCitasService = reservaCitasService;
        this.estadoService = estadoService;
        this.especialidadService = especialidadService;
        this.medicoService = medicoService;
    }

    @GetMapping("/historial")
    public String mostrarHistorial(HttpSession session, Model model) {

        Long idUsuario = (Long)session.getAttribute("idUsuarioLoggeado");
        Long idRolUsuario = (Long)session.getAttribute("idRolUsuarioLoggeado");
        boolean esAdmin = (Long)session.getAttribute("idRolUsuarioLoggeado") == 2 ? true : false;
        String nombreCompletoUsuarioLoggeado = (String)session.getAttribute("nombreUsuarioLoggeado") + " " + (String)session.getAttribute("primerApellidoUsuarioLoggeado") + " " + (String)session.getAttribute("segundoApellidoUsuarioLoggeado");

        boolean mostrarNotificacion = (boolean)session.getAttribute("mostrarNotificacion");
        String origenNotificacion = (String)session.getAttribute("origen");

        if(mostrarNotificacion && origenNotificacion.equals("historial")) {
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

        Long idRolUsuario = (Long)session.getAttribute("idRolUsuarioLoggeado");
        boolean esAdmin = (Long)session.getAttribute("idRolUsuarioLoggeado") == 2 ? true : false;
        String nombreCompletoUsuarioLoggeado = (String)session.getAttribute("nombreUsuarioLoggeado") + " " + (String)session.getAttribute("primerApellidoUsuarioLoggeado") + " " + (String)session.getAttribute("segundoApellidoUsuarioLoggeado");

        if(esAdmin) {
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
            model.addAttribute("listaEstados", estadoService.consultarEstadosCitas());
            model.addAttribute("listaEspecialidades", especialidadService.listaEspecialidadesConCitas());
            model.addAttribute("listaMedicos", medicoService.listaMedicosConCitas());
            model.addAttribute("listaCitas", citaPacienteService.consultaCitasPacientes(1, 5));
        }
        return "citasMedicas";
    }

    @GetMapping("/citas/confirm/{idCita}")
    public String confirmarCita(@PathVariable("idCita") Long idCita, HttpSession session, Model model) {

        Long idUsuario = (Long)session.getAttribute("idUsuarioLoggeado");
        boolean esAdmin = (Long)session.getAttribute("idRolUsuarioLoggeado") == 2 ? true : false;
        boolean resultadoConfirmacionCita = reservaCitasService.confirmarCita(idCita, idUsuario);
        session.setAttribute("mostrarNotificacion", true);
        session.setAttribute("origen", esAdmin ? "citas" : "historial");

        if(resultadoConfirmacionCita) {
            session.setAttribute("tipoNotificacion", "success");
            session.setAttribute("titulo", "¡Cita confirmada!");
            session.setAttribute("detalle", esAdmin ? "La cita ha sido agendada correctamente." : "Tu cita ha sido agendada correctamente.");
        }else {
            session.setAttribute("tipoNotificacion", "warning");
            session.setAttribute("titulo", "Cita sin confirmar.");
            session.setAttribute("detalle", esAdmin ? "La cita no ha sido confirmada. Inténtelo nuevamente." : "Tu cita no ha sido confirmada. Inténtelo nuevamente.");
        }
        return "redirect:/" + (esAdmin ? "citas" : "historial");
    }

    @GetMapping("/citas/cancel/{idCita}")
    public String cancelarCita(@PathVariable("idCita") Long idCita, HttpSession session, Model model) {

        Long idUsuario = (Long)session.getAttribute("idUsuarioLoggeado");
        boolean esAdmin = (Long)session.getAttribute("idRolUsuarioLoggeado") == 2 ? true : false;
        boolean resultadoCancelacionCita = reservaCitasService.cancelarCita(idCita, idUsuario);
        session.setAttribute("mostrarNotificacion", true);
        session.setAttribute("origen", esAdmin ? "citas" : "historial");

        if(resultadoCancelacionCita) {
            session.setAttribute("tipoNotificacion", "success");
            session.setAttribute("titulo", "¡Cita cancelada!");
            session.setAttribute("detalle", esAdmin ? "La cita ha sido cancelada correctamente." : "Tu cita ha sido cancelada correctamente.");
        }else {
            session.setAttribute("tipoNotificacion", "warning");
            session.setAttribute("titulo", "Cita sin cancelar.");
            session.setAttribute("detalle", esAdmin ? "La cita no ha sido cancelada. Inténtelo nuevamente." : "Tu cita no ha sido cancelada. Inténtelo nuevamente.");
        }
        return "redirect:/" + (esAdmin ? "citas" : "historial");
    }
}