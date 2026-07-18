package com.hospital.citas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import com.hospital.citas.model.dto.HorarioMedicoDTO;
import com.hospital.citas.service.DisponibilidadMedicoService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class DisponibilidadMedicoController {
    
    private final DisponibilidadMedicoService disponibilidadMedicoService;

    DisponibilidadMedicoController(DisponibilidadMedicoService disponibilidadMedicoService) {
        this.disponibilidadMedicoService = disponibilidadMedicoService;
    }

    @GetMapping("/configuracion-horario")
    public String configurarHorarioMedico(HttpSession session, Model model) {

        boolean mostrarNotificacion  = (boolean)session.getAttribute("mostrarNotificacion");
        String origenNotificacion = (String)session.getAttribute("origen");

        if(mostrarNotificacion && origenNotificacion.equals("configHorarios")) {
            model.addAttribute("mostrarNotificacion", true);
            model.addAttribute("tipoNotificacion", (String)session.getAttribute("tipoNotificacion"));
            model.addAttribute("titulo", (String)session.getAttribute("titulo"));
            model.addAttribute("detalle", (String)session.getAttribute("detalle"));

            session.setAttribute("mostrarNotificacion", false);
            session.setAttribute("origen", "");
        }

        boolean esAdmin = (Long)session.getAttribute("idRolUsuarioLoggeado") == 2 ? true : false;
        String nombreCompletoUsuarioLoggeado = (String)session.getAttribute("nombreUsuarioLoggeado") + " " + (String)session.getAttribute("primerApellidoUsuarioLoggeado") + " " + (String)session.getAttribute("segundoApellidoUsuarioLoggeado");

        model.addAttribute("usuarioEsAdmin", esAdmin);
        model.addAttribute("nombreCompletoUsuario", nombreCompletoUsuarioLoggeado);
        model.addAttribute("horario", new HorarioMedicoDTO());
        // CARGAR ESPECIALIDADES CON DOCTORES
        model.addAttribute("listaEspecialidades", disponibilidadMedicoService.listaEspecialidadesConMedico(4L));
        return "configuracionHorarios";
    }

    @PostMapping("/procesar-horario-medico")
    public String procesarHorarioMedico(@Valid @ModelAttribute("horario") HorarioMedicoDTO horario, BindingResult bindingResult, HttpSession session, Model model) {
        
        Long idUsuarioLoggeado = (Long)session.getAttribute("idUsuarioLoggeado");
        boolean esAdmin = (Long)session.getAttribute("idRolUsuarioLoggeado") == 2 ? true : false;
        String nombreCompletoUsuarioLoggeado = (String)session.getAttribute("nombreUsuarioLoggeado") + " " + (String)session.getAttribute("primerApellidoUsuarioLoggeado") + " " + (String)session.getAttribute("segundoApellidoUsuarioLoggeado");

        model.addAttribute("usuarioEsAdmin", esAdmin);
        model.addAttribute("nombreCompletoUsuario", nombreCompletoUsuarioLoggeado);
        model.addAttribute("listaEspecialidades", disponibilidadMedicoService.listaEspecialidadesConMedico(4L));

        if(bindingResult.hasErrors()) {
            model.addAttribute("horario", horario);
            if(horario.getIdMedico() != null) model.addAttribute("nombreMedicoSeleccionado", disponibilidadMedicoService.consultaNombreMedicoPorId(horario.getIdMedico()));
            return "configuracionHorarios";
        }

        // SE VALIDAN LAS HORAS
        boolean horasValidas = disponibilidadMedicoService.horasAtencionSonValidas(horario);
        if(!horasValidas) {
            model.addAttribute("horario", horario);
            if(horario.getIdMedico() != null) model.addAttribute("nombreMedicoSeleccionado", disponibilidadMedicoService.consultaNombreMedicoPorId(horario.getIdMedico()));
            model.addAttribute("mostrarNotificacion", true);
            model.addAttribute("tipoNotificacion", "warning");
            model.addAttribute("titulo", "¡Horario no válido!");
            model.addAttribute("detalle", "Las horas ya se encuentran cubiertas completa o parcialmente por otro registro.");
            return "configuracionHorarios";
        }
        
        boolean horarioProcesado = disponibilidadMedicoService.procesarHorarioMedico(horario, idUsuarioLoggeado);
        if(horarioProcesado) {
            session.setAttribute("mostrarNotificacion", true);
            session.setAttribute("origen", "configHorarios");
            session.setAttribute("tipoNotificacion", "success");
            session.setAttribute("titulo", "¡Horario procesado!");
            session.setAttribute("detalle", "El horario ha sido registrado correctamente.");
        }else {
            model.addAttribute("horario", horario);
            model.addAttribute("nombreMedicoSeleccionado", disponibilidadMedicoService.consultaNombreMedicoPorId(horario.getIdMedico()));
            model.addAttribute("mostrarNotificacion", true);
            model.addAttribute("tipoNotificacion", "warning");
            model.addAttribute("titulo", "¡Horario no procesado!");
            model.addAttribute("detalle", "Ocurrió un problema, el horario no fue procesado. Inténtelo nuevamente.");
        }

        return horarioProcesado ? "redirect:/configuracion-horario" : "configuracionHorarios";
    }


}