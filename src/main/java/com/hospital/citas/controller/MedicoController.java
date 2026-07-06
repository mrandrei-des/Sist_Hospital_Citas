package com.hospital.citas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import com.hospital.citas.model.dto.MedicoDTO;
import com.hospital.citas.service.MedicoService;
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
    public String getMethodName(Model model) {
        model.addAttribute("medico", new MedicoDTO());
        model.addAttribute("listaMedicos", medicoService.listaMedicoRegistradoDTOs());
        model.addAttribute("listaEspecialidades", medicoService.listaEspecialidadesDTO());
        return "registroMedicos";
    }

    @PostMapping("/registro-medico")
    public String registroMedico(@Valid @ModelAttribute("medico") MedicoDTO medico, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()) {
            model.addAttribute("medico", medico);
            model.addAttribute("listaMedicos", medicoService.listaMedicoRegistradoDTOs());
            model.addAttribute("listaEspecialidades", medicoService.listaEspecialidadesDTO());
            return "registroMedicos";
        }

        model.addAttribute("mostrarNotificacion", true);
        if(medicoService.procesarMedico(medico)) {
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
    public String buscarMedicoPorId(@PathVariable("id") Long idMedico, Model model) {
        model.addAttribute("medico", medicoService.buscarPorId(idMedico));
        model.addAttribute("listaMedicos", medicoService.listaMedicoRegistradoDTOs());
        model.addAttribute("listaEspecialidades", medicoService.listaEspecialidadesDTO());
        return "registroMedicos";
    }
    
    @GetMapping("/deshabilitar-medico/{id}")
    public String deshabilitarMedico(@PathVariable("id") Long idMedico, Model model) {
        medicoService.eliminarPorId(idMedico);
        model.addAttribute("medico", new MedicoDTO());
        model.addAttribute("listaMedicos", medicoService.listaMedicoRegistradoDTOs());
        model.addAttribute("listaEspecialidades", medicoService.listaEspecialidadesDTO());
        model.addAttribute("mostrarNotificacion", true);
        model.addAttribute("mensajeNotificacion", "¡Médico eliminado!");
        return "registroMedicos";
    }
}

//mostrarNotificacion - mensajeNotificacion
