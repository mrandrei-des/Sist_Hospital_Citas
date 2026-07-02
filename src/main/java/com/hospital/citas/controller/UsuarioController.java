package com.hospital.citas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.hospital.citas.model.entity.Usuario;
import com.hospital.citas.service.RolService;
import com.hospital.citas.service.TipoIdentificacionService;
import com.hospital.citas.service.UsuarioService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

// AÚN NO SE HA DEFINIDO QUÉ SE VA A HACER CON ESTE CONTROLLER
@Controller
public class UsuarioController {
    private final UsuarioService usuarioService;
    private final TipoIdentificacionService tipoIdentificacionService;
    private final RolService rolService;

    UsuarioController(UsuarioService usuarioService, TipoIdentificacionService tipoIdentificacionService, RolService rolService) {
        this.usuarioService = usuarioService;
        this.tipoIdentificacionService = tipoIdentificacionService;
        this.rolService = rolService;
    }

    @PostMapping("/cuentaNueva")
    public String crearCuenta(@Valid @ModelAttribute("cuentaNueva") Usuario usuario, BindingResult bindingResult,  @RequestParam("origenPeticion") String origenPeticion, Model model) {

        if(bindingResult.hasErrors()) {
            model.addAttribute("cuentaNueva", usuario);
            model.addAttribute("listaTipoIdentificacion", tipoIdentificacionService.consultarTiposDeIdentificacion());
            model.addAttribute("listaRoles", rolService.consultarRolesDTO());
            model.addAttribute("esUnPaciente", origenPeticion);
            return "formularioCuenta";
        }
        return "/";
    }

/*
    @GetMapping("/mostarHome/{pantallaHome}")
    public String mostrarHomeAdministrador(@PathVariable String pantallaHome) {
        return pantallaHome;
    }*/

    @GetMapping("/recuperarContrasenna")
    public String mostrarFormularioRecuperacionContrasenna() {
        return "solicitudCambioContrasena";
    }
}