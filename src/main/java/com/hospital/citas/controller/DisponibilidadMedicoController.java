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
        String origenNotificacion = (String)session.getAttribute(("origen"));
        if(mostrarNotificacion && origenNotificacion == "configHorarios") {
            String mensajeNotificacion = (String)session.getAttribute("mensajeNotificacion");
            model.addAttribute("mostrarNotificacion", true);
            model.addAttribute("mensajeNotificacion", mensajeNotificacion);

            session.setAttribute("mostrarNotificacion", false);
            session.setAttribute("mensajeNotificacion", "");
            session.setAttribute("origen", "");
        }
        
        boolean esAdmin = (Long)session.getAttribute("idRolUsuarioLoggeado") == 2 ? true : false;
        String nombreCompletoUsuarioLoggeado = (String)session.getAttribute("nombreUsuarioLoggeado") + " " + (String)session.getAttribute("primerApellidoUsuarioLoggeado") + " " + (String)session.getAttribute("segundoApellidoUsuarioLoggeado");

        model.addAttribute("usuarioEsAdmin", esAdmin);
        model.addAttribute("nombreCompletoUsuario", nombreCompletoUsuarioLoggeado);
        model.addAttribute("horario", new HorarioMedicoDTO());
        model.addAttribute("listaEspecialidades", disponibilidadMedicoService.listaEspecialidades(4L));
        return "configuracionHorarios";
    }

    @PostMapping("/procesar-horario-medico")
    public String procesarHorarioMedico(@Valid @ModelAttribute("horario") HorarioMedicoDTO horario, BindingResult bindingResult, HttpSession session, Model model) {
        
        Long idUsuarioLoggeado = (Long)session.getAttribute("idUsuarioLoggeado");
        boolean esAdmin = (Long)session.getAttribute("idRolUsuarioLoggeado") == 2 ? true : false;
        String nombreCompletoUsuarioLoggeado = (String)session.getAttribute("nombreUsuarioLoggeado") + " " + (String)session.getAttribute("primerApellidoUsuarioLoggeado") + " " + (String)session.getAttribute("segundoApellidoUsuarioLoggeado");

        model.addAttribute("usuarioEsAdmin", esAdmin);
        model.addAttribute("nombreCompletoUsuario", nombreCompletoUsuarioLoggeado);
        model.addAttribute("listaEspecialidades", disponibilidadMedicoService.listaEspecialidades(4L));

        if(bindingResult.hasErrors()) {
            model.addAttribute("horario", horario);
            model.addAttribute("nombreMedicoSeleccionado", disponibilidadMedicoService.consultaNombreMedicoPorId(horario.getIdMedico()));
            return "configuracionHorarios";
        }

        if(disponibilidadMedicoService.procesarHorarioMedico(horario, idUsuarioLoggeado)) {
            session.setAttribute("mostrarNotificacion", true);
            session.setAttribute("mensajeNotificacion", "¡Horario procesado!");
            session.setAttribute("origen", "configHorarios");
        }
        return "redirect:/configuracion-horario";
    }
}