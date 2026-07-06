package com.hospital.citas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.hospital.citas.model.entity.Rol;
import com.hospital.citas.model.entity.TipoIdentificacion;
import com.hospital.citas.model.entity.Usuario;
import com.hospital.citas.service.TipoIdentificacionService;

import jakarta.servlet.http.HttpSession;

@Controller
public class PacienteController {
    private final TipoIdentificacionService tipoIdentificacionService;
    PacienteController(TipoIdentificacionService tipoIdentificacionService) {
        this.tipoIdentificacionService = tipoIdentificacionService;
    }

    @GetMapping("/registrarPaciente")
    public String mostrarFormularioRegistroPaciente(HttpSession session, Model model) {

        boolean esAdmin = (Long)session.getAttribute("idRolUsuarioLoggeado") == 2 ? true : false;
        model.addAttribute("usuarioEsAdmin", esAdmin);
        model.addAttribute("idRolUsuario", session.getAttribute("idUsuarioLoggeado"));

        Usuario pacienteNuevo = new Usuario();
        Rol rolPaciente = new Rol();
        TipoIdentificacion tipoIdentificacionPaciente = new TipoIdentificacion();
        rolPaciente.setId(1L);
        pacienteNuevo.setRol(rolPaciente);
        pacienteNuevo.setTipoIdentificacion(tipoIdentificacionPaciente);
        
        model.addAttribute("cuentaNueva", pacienteNuevo);
        model.addAttribute("listaTipoIdentificacion", tipoIdentificacionService.consultarTiposDeIdentificacion());
        model.addAttribute("esUnPaciente", "S");
        return "formularioCuenta";
    }
}