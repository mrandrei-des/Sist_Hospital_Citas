package com.hospital.citas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import com.hospital.citas.model.dto.EspecialidadDTO;
import com.hospital.citas.service.EspecialidadService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class EspecialidadController {

    private final EspecialidadService especialidadService;

    EspecialidadController(EspecialidadService especialidadService) {
        this.especialidadService = especialidadService;
    }
    
    @GetMapping("/especialidades")
    public String mostrarPaginaEspecialidades(HttpSession session, Model model) {

        boolean esAdmin = (Long)session.getAttribute("idRolUsuarioLoggeado") == 2 ? true : false;
        String nombreCompletoUsuarioLoggeado = (String)session.getAttribute("nombreUsuarioLoggeado") + " " + (String)session.getAttribute("primerApellidoUsuarioLoggeado") + " " + (String)session.getAttribute("segundoApellidoUsuarioLoggeado");

        boolean mostrarMensaje = (boolean)session.getAttribute("mostrarNotificacion");
        String origenNotificacion = (String)session.getAttribute("origen");
        
        if(mostrarMensaje && origenNotificacion.equals("especialidades")) {
            model.addAttribute("mostrarNotificacion", true);
            model.addAttribute("tipoNotificacion", (String)session.getAttribute("tipoNotificacion"));
            model.addAttribute("titulo", (String)session.getAttribute("titulo"));
            model.addAttribute("detalle", (String)session.getAttribute("detalle"));

            session.setAttribute("mostrarNotificacion", false);
            session.setAttribute("origen", "");
        }

        model.addAttribute("nombreCompletoUsuario", nombreCompletoUsuarioLoggeado);
        model.addAttribute("usuarioEsAdmin", esAdmin);
        model.addAttribute("idRolUsuario", session.getAttribute("idUsuarioLoggeado"));
        model.addAttribute("especialidad", new EspecialidadDTO());
        model.addAttribute("listaEspecialidades", especialidadService.listarEspecialidades(4L));
        model.addAttribute("listaUltEspecialidades", especialidadService.listarUltimaEspecialidadRegistradaDTOs());
        return "registroEspecialidades";
    }

    @PostMapping("/registro-especialidad")
    public String registroEspecialidad(@Valid @ModelAttribute("especialidad") EspecialidadDTO especialidad, BindingResult bindingResult, HttpSession session,  Model model) {
        
        Long idUsuarioLoggeado = (Long)session.getAttribute("idUsuarioLoggeado");
        boolean esAdmin = (Long)session.getAttribute("idRolUsuarioLoggeado") == 2 ? true : false;
        String nombreCompletoUsuarioLoggeado = (String)session.getAttribute("nombreUsuarioLoggeado") + " " + (String)session.getAttribute("primerApellidoUsuarioLoggeado") + " " + (String)session.getAttribute("segundoApellidoUsuarioLoggeado");

        model.addAttribute("nombreCompletoUsuario", nombreCompletoUsuarioLoggeado);
        model.addAttribute("usuarioEsAdmin", esAdmin);
        model.addAttribute("idRolUsuario", session.getAttribute("idUsuarioLoggeado"));

        if(bindingResult.hasErrors()) {
            model.addAttribute("especialidad", especialidad);
            model.addAttribute("listaEspecialidades", especialidadService.listarEspecialidades(4L));
            model.addAttribute("listaUltEspecialidades", especialidadService.listarUltimaEspecialidadRegistradaDTOs());
            return "registroEspecialidades";
        }
        
        if(especialidadService.registrarEspecialidad(especialidad, idUsuarioLoggeado)) {
            model.addAttribute("especialidad", new EspecialidadDTO());
            session.setAttribute("mostrarNotificacion", true);
            session.setAttribute("tipoNotificacion", "success");
            session.setAttribute("titulo", "¡Especialidad procesada!");
            session.setAttribute("detalle", "La especialidad ha sido procesada correctamente");
            session.setAttribute("origen", "especialidades");
        }else {
            model.addAttribute("especialidad", especialidad);
            session.setAttribute("tipoNotificacion", "warning");
            session.setAttribute("titulo", "¡Especialidad no procesada!");
            session.setAttribute("detalle", "Ocurrió un problema, la especialida no se procesó. Inténtelo nuevamente.");
            session.setAttribute("origen", "especialidades");
        }
        
        model.addAttribute("listaEspecialidades", especialidadService.listarEspecialidades(4L));
        model.addAttribute("listaUltEspecialidades", especialidadService.listarUltimaEspecialidadRegistradaDTOs());
        return "registroEspecialidades";
    }

    @GetMapping("/buscar-especialidad/{id}")
    public String buscarEspecialidadPorId(@PathVariable("id") Long idEspecialidad, HttpSession session, Model model) {

        boolean esAdmin = (Long)session.getAttribute("idRolUsuarioLoggeado") == 2 ? true : false;
        String nombreCompletoUsuarioLoggeado = (String)session.getAttribute("nombreUsuarioLoggeado") + " " + (String)session.getAttribute("primerApellidoUsuarioLoggeado") + " " + (String)session.getAttribute("segundoApellidoUsuarioLoggeado");

        model.addAttribute("nombreCompletoUsuario", nombreCompletoUsuarioLoggeado);
        model.addAttribute("usuarioEsAdmin", esAdmin);
        model.addAttribute("idRolUsuario", session.getAttribute("idUsuarioLoggeado"));

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
