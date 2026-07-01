package com.hospital.citas.controller;

import org.springframework.stereotype.Controller;
import com.hospital.citas.service.TipoIdentificacionService;
import com.hospital.citas.service.UsuarioService;
import org.springframework.web.bind.annotation.GetMapping;

// AÚN NO SE HA DEFINIDO QUÉ SE VA A HACER CON ESTE CONTROLLER
@Controller
public class UsuarioController {
    private final UsuarioService usuarioService;

    UsuarioController(UsuarioService usuarioService, TipoIdentificacionService tipoIdentificacionService) {
        this.usuarioService = usuarioService;
    }

    // ESTO SE VA A HACER DESDE EL SPRING BOOT SECUTITY
    /* 
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
    }*/

    @GetMapping("/recuperarContrasenna")
    public String mostrarFormularioRecuperacionContrasenna() {
        return "solicitudCambioContrasena";
    }
}