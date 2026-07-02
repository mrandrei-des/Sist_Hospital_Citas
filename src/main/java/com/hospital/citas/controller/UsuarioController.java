package com.hospital.citas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.hospital.citas.model.dto.CodigoResetContrasennaDTO;
import com.hospital.citas.model.dto.UsuarioInicioSesionDTO;
import com.hospital.citas.model.entity.Estado;
import com.hospital.citas.model.entity.Usuario;
import com.hospital.citas.service.RolService;
import com.hospital.citas.service.TipoIdentificacionService;
import com.hospital.citas.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;


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

        Estado estado = new Estado();
        estado.setId(4L);
        usuario.setEstado(estado);
        usuarioService.crearCuenta(usuario);

        if(origenPeticion.equals("S")) {
            return "redirect:/";
        }else {
            return "redirect:/homeAdmin";
        }
    }

    @PostMapping("/procesarRecuperacion")
    public String mostrarFormularioRecuperacionContrasenna(@Valid @ModelAttribute("usuario") UsuarioInicioSesionDTO usuario, BindingResult bindingResult, Model model) {       
        if(bindingResult.hasErrors()) {
            model.addAttribute("usuario", usuario);
            return "solicitudCambioContrasena";
        }

        usuarioService.procesarRecuperacionContrasenna(usuario);
        model.addAttribute("correo", usuario.getCorreo());
        model.addAttribute("codigoOTP", new CodigoResetContrasennaDTO());
        model.addAttribute("mostrarMensajeDeIncorrecto", false);
        model.addAttribute("mostrarMensajeDeExpirado", false);
        return "verificacionCodigo";
    }

    @PostMapping("/procesarVerificacion")
    public String postMethodName(@Valid @ModelAttribute("codigoOTP") CodigoResetContrasennaDTO codigoOTP, @RequestParam("correo") String correoUsuario, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()) {
            model.addAttribute("correo", correoUsuario);
            model.addAttribute("codigoOTP", codigoOTP);
            model.addAttribute("mostrarMensajeDeIncorrecto", false);
            model.addAttribute("mostrarMensajeDeExpirado", false);
            return "verificacionCodigo";
        }

        // EJECUTAR LA VALIDACIÓN DEL CÓDIGO OTP, TANTO VALOR COMO EXPIRACIÓN

        boolean codigoEsCorrecto = usuarioService.codigoSeguridadEsValido(codigoOTP, correoUsuario);

        boolean codigoEstaActivo = usuarioService.codigoSeguridadEstaActivo(codigoOTP, correoUsuario);

        if(!codigoEsCorrecto || !codigoEstaActivo) {
            model.addAttribute("correo", correoUsuario);
            model.addAttribute("codigoOTP", codigoOTP);
            model.addAttribute("mostrarMensajeDeIncorrecto", !codigoEsCorrecto);
            model.addAttribute("mostrarMensajeDeExpirado", !codigoEstaActivo);
            return "verificacionCodigo";
        }

        // EL CÓDIGO ES CORRECTO Y NO HA VENCIDO, SE PROCEDE A PROCESAR
        usuarioService.procesarCodigoSeguridad(codigoOTP, correoUsuario);

        UsuarioInicioSesionDTO usuarioDTO = new UsuarioInicioSesionDTO();
        usuarioDTO.setCorreo(correoUsuario);
        model.addAttribute("usuario", usuarioDTO);
        return "cambioContrasenna";
    }

    @PostMapping("/procesarCambioContrasenna")
    public String postMethodName(@Valid @ModelAttribute("usuario") UsuarioInicioSesionDTO usuarioDTO, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()) {
            model.addAttribute("usuario", usuarioDTO);
            return "cambioContrasenna";
        }

        //  PROCESAR EL CAMBIO DE LA CONTRASEÑA
        

        return "redirect:/";
    }
    
}