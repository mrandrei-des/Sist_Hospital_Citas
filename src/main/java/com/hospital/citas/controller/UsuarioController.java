package com.hospital.citas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import com.hospital.citas.model.entity.Usuario;
import com.hospital.citas.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UsuarioController {
    private UsuarioService usuarioService;

    UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/login")
    public String iniciarSesion(@Valid @ModelAttribute Usuario usuario, BindingResult bindingResult, Model model) {

        if(bindingResult.hasErrors()) {
            return "inicioSesion";
        }

        if(usuarioService.validarCredenciales(usuario)) {
            // SE DEBE AGREGAR LA INFORMACIÓN DEL USUARIO A LAS VARIABLES DE SESIÓN
            return "redirect:/mostarHome/" + usuarioService.consultarPaginaInicioPorRol(usuario);
        }

        model.addAttribute("mostrarError", true);
        return "inicioSesion";
    }

    @GetMapping("/mostarHome/{pantallaHome}")
    public String mostrarHomeAdministrador(@PathVariable String pantallaHome) {
        return pantallaHome;
    }

    @GetMapping("/registrarPaciente")
    public String mostrarFormularioRegistroPaciente(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "registroPacientes";
    }

    @GetMapping("/recuperarContrasenna")
    public String mostrarFormularioRecuperacionContrasenna() {
        return "solicitudCambioContrasena";
    }
}