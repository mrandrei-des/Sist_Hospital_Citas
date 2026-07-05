package com.hospital.citas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import com.hospital.citas.model.dto.EspecialidadDTO;
import com.hospital.citas.service.EspecialidadService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class EspecialidadController {

    private final EspecialidadService especialidadService;

    EspecialidadController(EspecialidadService especialidadService) {
        this.especialidadService = especialidadService;
    }
    
    @GetMapping("/especialidades")
    public String mostrarPaginaEspecialidades(Model model) {
        model.addAttribute("especialidad", new EspecialidadDTO());
        model.addAttribute("listaEspecialidades", especialidadService.listarEspecialidades(4L));
        model.addAttribute("listaUltEspecialidades", especialidadService.listarUltimaEspecialidadRegistradaDTOs());
        // Cargar las especialidades registradas y las últimas
        return "registroEspecialidades";
    }

    @PostMapping("/registro-especialidad")
    public String registroEspecialidad(@Valid @ModelAttribute("especialidad") EspecialidadDTO especialidad, BindingResult bindingResult,  Model model) {
        if(bindingResult.hasErrors()) {
            model.addAttribute("especialidad", especialidad);
            model.addAttribute("listaEspecialidades", especialidadService.listarEspecialidades(4L));
            model.addAttribute("listaUltEspecialidades", especialidadService.listarUltimaEspecialidadRegistradaDTOs());
            return "registroEspecialidades";
        }
        
        model.addAttribute("mostrarNotificacion", true);
        if(especialidadService.registrarEspecialidad(especialidad)) {
            model.addAttribute("especialidad", new EspecialidadDTO());
            model.addAttribute("mensajeNotificacion", "¡Especialidad procesada!");
        }else {
            model.addAttribute("especialidad", especialidad);
            model.addAttribute("mensajeNotificacion", "¡Ocurrió un problema!");
        }
        
        model.addAttribute("listaEspecialidades", especialidadService.listarEspecialidades(4L));
        model.addAttribute("listaUltEspecialidades", especialidadService.listarUltimaEspecialidadRegistradaDTOs());
        return "registroEspecialidades";
    }

    @GetMapping("/buscar-especialidad/{id}")
    public String buscarEspecialidadPorId(@PathVariable("id") Long idEspecialidad, Model model) {
        if (idEspecialidad > 0) {
            model.addAttribute("especialidad", especialidadService.buscarPorId(idEspecialidad));
        }else {
            model.addAttribute("especialidad", new EspecialidadDTO());
        }
        model.addAttribute("listaEspecialidades", especialidadService.listarEspecialidades(4L));
        model.addAttribute("listaUltEspecialidades", especialidadService.listarUltimaEspecialidadRegistradaDTOs());
        return "registroEspecialidades";
    }
}
