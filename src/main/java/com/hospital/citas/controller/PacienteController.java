package com.hospital.citas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.hospital.citas.model.entity.Rol;
import com.hospital.citas.model.entity.TipoIdentificacion;
import com.hospital.citas.model.entity.Usuario;
import com.hospital.citas.service.TipoIdentificacionService;

import jakarta.servlet.http.HttpSession;

// Controlador encargado para el enrutamiento del formulario para la creación de pacientes desde fuera del login.
@Controller
public class PacienteController {
    private final TipoIdentificacionService tipoIdentificacionService;
    PacienteController(TipoIdentificacionService tipoIdentificacionService) {
        this.tipoIdentificacionService = tipoIdentificacionService;
    }

    @GetMapping("/registrarPaciente")
    public String mostrarFormularioRegistroPaciente(HttpSession session, Model model) {

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