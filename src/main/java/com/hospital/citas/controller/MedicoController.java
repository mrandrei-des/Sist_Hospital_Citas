package com.hospital.citas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import com.hospital.citas.model.dto.MedicoDTO;
import com.hospital.citas.service.MedicoService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MedicoController {
    private final MedicoService medicoService;

    MedicoController(MedicoService medicoService) {
        this.medicoService = medicoService;
    }

    @GetMapping("/medicos")
    public String getMethodName(HttpSession session, Model model) {

        boolean esAdmin = (Long)session.getAttribute("idRolUsuarioLoggeado") == 2 ? true : false;
        String nombreCompletoUsuarioLoggeado = (String)session.getAttribute("nombreUsuarioLoggeado") + " " + (String)session.getAttribute("primerApellidoUsuarioLoggeado") + " " + (String)session.getAttribute("segundoApellidoUsuarioLoggeado");

        model.addAttribute("nombreCompletoUsuario", nombreCompletoUsuarioLoggeado);
        model.addAttribute("usuarioEsAdmin", esAdmin);
        model.addAttribute("idRolUsuario", session.getAttribute("idUsuarioLoggeado"));

        model.addAttribute("medico", new MedicoDTO());
        model.addAttribute("listaMedicos", medicoService.listaMedicoRegistradoDTOs());
        model.addAttribute("listaEspecialidades", medicoService.listaEspecialidadesDTO());
        return "registroMedicos";
    }

    @PostMapping("/registro-medico")
    public String registroMedico(@Valid @ModelAttribute("medico") MedicoDTO medico, BindingResult bindingResult, HttpSession session, Model model) {
        
        Long idUsuarioLoggeado = (Long)session.getAttribute("idUsuarioLoggeado");
        boolean esAdmin = (Long)session.getAttribute("idRolUsuarioLoggeado") == 2 ? true : false;
        String nombreCompletoUsuarioLoggeado = (String)session.getAttribute("nombreUsuarioLoggeado") + " " + (String)session.getAttribute("primerApellidoUsuarioLoggeado") + " " + (String)session.getAttribute("segundoApellidoUsuarioLoggeado");

        model.addAttribute("nombreCompletoUsuario", nombreCompletoUsuarioLoggeado);
        model.addAttribute("usuarioEsAdmin", esAdmin);
        model.addAttribute("idRolUsuario", session.getAttribute("idUsuarioLoggeado"));

        if(bindingResult.hasErrors()) {
            model.addAttribute("medico", medico);
            model.addAttribute("listaMedicos", medicoService.listaMedicoRegistradoDTOs());
            model.addAttribute("listaEspecialidades", medicoService.listaEspecialidadesDTO());
            return "registroMedicos";
        }

        model.addAttribute("mostrarNotificacion", true);
        if(medicoService.procesarMedico(medico, idUsuarioLoggeado)) {
            model.addAttribute("medico", new MedicoDTO());
            model.addAttribute("mensajeNotificacion", "¡Médico procesado!");
        }else {
            model.addAttribute("medico", medico);
            model.addAttribute("mensajeNotificacion", "¡Ocurrió un problema!");
        }
        model.addAttribute("listaMedicos", medicoService.listaMedicoRegistradoDTOs());
        model.addAttribute("listaEspecialidades", medicoService.listaEspecialidadesDTO());
        return "registroMedicos";
    }

    @GetMapping("/buscar-medico/{id}")
    public String buscarMedicoPorId(@PathVariable("id") Long idMedico, HttpSession session, Model model) {

        boolean esAdmin = (Long)session.getAttribute("idRolUsuarioLoggeado") == 2 ? true : false;
        model.addAttribute("usuarioEsAdmin", esAdmin);
        model.addAttribute("idRolUsuario", session.getAttribute("idUsuarioLoggeado"));

        model.addAttribute("medico", medicoService.buscarPorId(idMedico));
        model.addAttribute("listaMedicos", medicoService.listaMedicoRegistradoDTOs());
        model.addAttribute("listaEspecialidades", medicoService.listaEspecialidadesDTO());
        return "registroMedicos";
    }
    
    @GetMapping("/deshabilitar-medico/{id}")
    public String deshabilitarMedico(@PathVariable("id") Long idMedico, HttpSession session, Model model) {
        
        Long idUsuarioLoggeado = (Long)session.getAttribute("idUsuarioLoggeado");
        boolean esAdmin = (Long)session.getAttribute("idRolUsuarioLoggeado") == 2 ? true : false;
        model.addAttribute("usuarioEsAdmin", esAdmin);
        model.addAttribute("idRolUsuario", session.getAttribute("idUsuarioLoggeado"));

        medicoService.eliminarPorId(idMedico, idUsuarioLoggeado);
        model.addAttribute("medico", new MedicoDTO());
        model.addAttribute("listaMedicos", medicoService.listaMedicoRegistradoDTOs());
        model.addAttribute("listaEspecialidades", medicoService.listaEspecialidadesDTO());
        model.addAttribute("mostrarNotificacion", true);
        model.addAttribute("mensajeNotificacion", "¡Médico eliminado!");
        return "registroMedicos";
    }
}