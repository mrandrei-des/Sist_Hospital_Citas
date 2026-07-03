package com.hospital.citas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.hospital.citas.model.entity.Rol;
import com.hospital.citas.model.entity.TipoIdentificacion;
import com.hospital.citas.model.entity.Usuario;
import com.hospital.citas.service.RolService;
import com.hospital.citas.service.TipoIdentificacionService;

// ESTE CONTROLLER VA A TENER A CARGO LO REFERENTE CON EL PACIENTE, REGISTRO, MODIFICACION DE DATOS, RESERVA DE CITAS Y VISUALIZACIÓN DE CITAS
@Controller
public class PacienteController {
    private final TipoIdentificacionService tipoIdentificacionService;
    private final RolService rolService;

    PacienteController(TipoIdentificacionService tipoIdentificacionService, RolService rolService) {
        this.tipoIdentificacionService = tipoIdentificacionService;
        this.rolService = rolService;
    }

    @GetMapping("/registrarPaciente")
    public String mostrarFormularioRegistroPaciente(Model model) {
        Usuario pacienteNuevo = new Usuario();
        Rol rolPaciente = new Rol();
        TipoIdentificacion tipoIdentificacionPaciente = new TipoIdentificacion();
        rolPaciente.setId(1L);
        pacienteNuevo.setRol(rolPaciente);
        pacienteNuevo.setTipoIdentificacion(tipoIdentificacionPaciente);
        
        model.addAttribute("cuentaNueva", pacienteNuevo);
        model.addAttribute("listaTipoIdentificacion", tipoIdentificacionService.consultarTiposDeIdentificacion());
        // PARA EVALUAR SI EL FORMULARIO LO VA A USAR UN ADMIN O UN PACIENTE NUEVO
        // EN EL ADMIN SE DEBE ENVIAR EL PARÁMETRO PERO CON VALOR N
        model.addAttribute("esUnPaciente", "S");
        return "formularioCuenta";
    }
}