package com.hospital.citas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.hospital.citas.model.dto.UsuarioInicioSesionDTO;
import com.hospital.citas.service.ReservaCitasService;

// Controlador para el enrutamiento de las páginas de login y recuperación de contraseña.
@Controller
public class InicioSesionController {

    private final ReservaCitasService reservaCitasService;

    InicioSesionController(ReservaCitasService reservaCitasService) {
        this.reservaCitasService = reservaCitasService;
    }

    @GetMapping("/")
    public String cargarInicioSesion(Model model){
        model.addAttribute("usuario", new UsuarioInicioSesionDTO());
        model.addAttribute("mostrarNotificacion", false);
        model.addAttribute("mensaje", "");
        reservaCitasService.botConfirmarCita();
        return "inicioSesion";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("usuario", new UsuarioInicioSesionDTO());
        model.addAttribute("mostrarNotificacion", false);
        model.addAttribute("mensaje", "");
        return "inicioSesion";
    }

    @GetMapping("/recuperarContrasenna")
    public String solicitudRecuperacionContrasenna(Model model) {
        UsuarioInicioSesionDTO usuarioDTO = new UsuarioInicioSesionDTO();
        usuarioDTO.setContrasenna("-");
        model.addAttribute("usuario", usuarioDTO);
        return "solicitudCambioContrasena";
    }
}
