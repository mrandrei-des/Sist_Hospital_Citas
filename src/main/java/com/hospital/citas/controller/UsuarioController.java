package com.hospital.citas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import com.hospital.citas.model.dto.UsuarioInicioSesionDTO;
import com.hospital.citas.model.dto.UsuarioMiPerfilDTO;
import com.hospital.citas.model.entity.CodigoResetContrasenna;
import com.hospital.citas.model.entity.Estado;
import com.hospital.citas.model.entity.Rol;
import com.hospital.citas.model.entity.TipoIdentificacion;
import com.hospital.citas.model.entity.Usuario;
import com.hospital.citas.service.EstadoService;
import com.hospital.citas.service.RolService;
import com.hospital.citas.service.TipoIdentificacionService;
import com.hospital.citas.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
public class UsuarioController {
    private final UsuarioService usuarioService;
    private final TipoIdentificacionService tipoIdentificacionService;
    private final RolService rolService;
    private final EstadoService estadoService;

    UsuarioController(UsuarioService usuarioService, TipoIdentificacionService tipoIdentificacionService, RolService rolService, EstadoService estadoService) {
        this.usuarioService = usuarioService;
        this.tipoIdentificacionService = tipoIdentificacionService;
        this.rolService = rolService;
        this.estadoService = estadoService;
    }

    @PostMapping("/cuentaNueva")
    public String crearCuenta(@Valid @ModelAttribute("cuentaNueva") Usuario usuario, BindingResult bindingResult,  @RequestParam("origenPeticion") String origenPeticion, HttpSession session, Model model) {

        if(bindingResult.hasErrors()) {
            model.addAttribute("cuentaNueva", usuario);
            model.addAttribute("listaTipoIdentificacion", tipoIdentificacionService.consultarTiposDeIdentificacion());
            model.addAttribute("listaRoles", rolService.consultarRolesDTO());
            model.addAttribute("listaEstados", estadoService.consultarEstadosUsuarios());
            model.addAttribute("esUnPaciente", origenPeticion);
            return "formularioCuenta";
        }

        if(origenPeticion.equals("S")) {
            Estado estado = new Estado();
            estado.setId(4L);
            usuario.setEstado(estado);
        }

        usuarioService.crearCuenta(usuario);

        if(origenPeticion.equals("S")) {
            return "redirect:/inicio";
        }else {
            return "redirect:/panel";
        }
    }

    @PostMapping("/procesarRecuperacion")
    public String mostrarFormularioRecuperacionContrasenna(@Valid @ModelAttribute("usuario") UsuarioInicioSesionDTO usuario, BindingResult bindingResult, HttpSession session, Model model) {

        if(bindingResult.hasErrors()) {
            model.addAttribute("usuario", usuario);
            return "solicitudCambioContrasena";
        }

        usuarioService.procesarRecuperacionContrasenna(usuario);
        model.addAttribute("correo", usuario.getCorreo());
        model.addAttribute("codigoOTP", new CodigoResetContrasenna());
        model.addAttribute("mostrarMensajeDeIncorrecto", false);
        model.addAttribute("mostrarMensajeDeExpirado", false);

        model.addAttribute("mostrarNotificacion", true);
        model.addAttribute("tipoNotificacion", "success");
        model.addAttribute("titulo", "¡Correo enviado!");
        model.addAttribute("detalle", "Correo de recuperación enviado");

        return "verificacionCodigo";
    }

    @PostMapping("/procesarVerificacion")
    public String procesarVerificacionCodigo(@Valid @ModelAttribute("codigoOTP") CodigoResetContrasenna codigoOTP, BindingResult bindingResult, @RequestParam("correo") String correoUsuario, HttpSession session, Model model) {

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

        if(!codigoEsCorrecto || !codigoEstaActivo ) {
            model.addAttribute("correo", correoUsuario);
            model.addAttribute("codigoOTP", codigoOTP);

            if(!codigoEsCorrecto) {
                model.addAttribute("mostrarMensajeDeIncorrecto", !codigoEsCorrecto);
                model.addAttribute("mostrarMensajeDeExpirado", false);
            }else {
                model.addAttribute("mostrarMensajeDeIncorrecto", !codigoEsCorrecto);
                model.addAttribute("mostrarMensajeDeExpirado", !codigoEstaActivo);
            }
            return "verificacionCodigo";
        }

        // EL CÓDIGO ES CORRECTO Y NO HA VENCIDO, SE PROCEDE A PROCESAR
        usuarioService.procesarCodigoSeguridad(codigoOTP, correoUsuario);

        UsuarioInicioSesionDTO usuarioDTO = new UsuarioInicioSesionDTO();
        usuarioDTO.setCorreo(correoUsuario);
        model.addAttribute("usuario", usuarioDTO);
        return "cambioContrasena";
    }

    @PostMapping("/procesarCambioContrasenna")
    public String procesarCambioContrasenna(@Valid @ModelAttribute("usuario") UsuarioInicioSesionDTO usuarioDTO, BindingResult bindingResult, HttpSession session, Model model) {

        if(bindingResult.hasErrors()) {
            model.addAttribute("usuario", usuarioDTO);
            return "cambioContrasena";
        }

        //  PROCESAR EL CAMBIO DE LA CONTRASEÑA
        boolean respuestaCambioContrasenna = usuarioService.procesarCambioContrasenna(usuarioDTO);
        model.addAttribute("mostrarNotificacion", true);
        if(respuestaCambioContrasenna) {
            model.addAttribute("tipoNotificacion", "success");
            model.addAttribute("titulo", "¡Contraseña actualizada!");
            model.addAttribute("detalle", "La contraseña ha sido actualizada correctamente");
        }else {
            model.addAttribute("tipoNotificacion", "warning");
            model.addAttribute("titulo", "¡Contraseña no procesada!");
            model.addAttribute("detalle", "Ocurrió un problema al procesar la contraseña. Inténtelo nuevamente.");
        }
        return "inicioSesion";
    }

    @PostMapping("/reenviarCodigoReset")
    public String reenviarCodigoCambioContrasenna(@ModelAttribute("codigoOTP") CodigoResetContrasenna codigoOTP, @RequestParam("correo") String correoUsuario, HttpSession session, Model model) {
        usuarioService.reenviarCodigoResetContrasenna(correoUsuario);
        model.addAttribute("correo", correoUsuario);
        model.addAttribute("codigoOTP", codigoOTP);
        model.addAttribute("mostrarMensajeDeIncorrecto", false);
        model.addAttribute("mostrarMensajeDeExpirado", false);

        model.addAttribute("mostrarNotificacion", true);
        model.addAttribute("tipoNotificacion", "success");
        model.addAttribute("titulo", "¡Correo reenviado!");
        model.addAttribute("detalle", "El correo de recuperación ha sido reenviado");

        return "verificacionCodigo";
    }

    @GetMapping("/registroUsuario")
    public String registrarUsuario(HttpSession session, Model model) {
        
        boolean esAdmin = (Long)session.getAttribute("idRolUsuarioLoggeado") == 2 ? true : false;
        model.addAttribute("usuarioEsAdmin", esAdmin);
        model.addAttribute("idRolUsuario", session.getAttribute("idUsuarioLoggeado"));

        Usuario usuarioNuevo = new Usuario();
        Rol rolUsuario = new Rol();
        TipoIdentificacion tipoIdentificacionUsuario = new TipoIdentificacion();
        Estado estadoUsuario = new Estado();

        usuarioNuevo.setId(1L);
        usuarioNuevo.setRol(rolUsuario);
        usuarioNuevo.setTipoIdentificacion(tipoIdentificacionUsuario);
        usuarioNuevo.setEstado(estadoUsuario);
        
        model.addAttribute("cuentaNueva", usuarioNuevo);
        model.addAttribute("listaTipoIdentificacion", tipoIdentificacionService.consultarTiposDeIdentificacion());
        model.addAttribute("esUnPaciente", "N");
        model.addAttribute("listaRoles", rolService.consultarRolesDTO());
        model.addAttribute("listaEstados", estadoService.consultarEstadosUsuarios());
        return "formularioCuenta";
    }

    @GetMapping("/cuenta-nueva")
    public String mostrarFormularioUsuarioNuevo(HttpSession session, Model model) {
        Long idUsuarioLoggeado = (Long)session.getAttribute("idUsuarioLoggeado");
        boolean esAdmin = (Long)session.getAttribute("idRolUsuarioLoggeado") == 2 ? true : false;
        String nombreCompletoUsuarioLoggeado = (String)session.getAttribute("nombreUsuarioLoggeado") + " " + (String)session.getAttribute("primerApellidoUsuarioLoggeado") + " " + (String)session.getAttribute("segundoApellidoUsuarioLoggeado");

        boolean mostrarMensaje = (boolean)session.getAttribute("mostrarNotificacion");
        String origenNotificacion = (String)session.getAttribute("origen");
        
        if(mostrarMensaje && origenNotificacion.equals("cuentaNueva")) {
            model.addAttribute("mostrarNotificacion", true);
            model.addAttribute("tipoNotificacion", (String)session.getAttribute("tipoNotificacion"));
            model.addAttribute("titulo", (String)session.getAttribute("titulo"));
            model.addAttribute("detalle", (String)session.getAttribute("detalle"));

            session.setAttribute("mostrarNotificacion", false);
            session.setAttribute("origen", "");
        }

        model.addAttribute("idRolUsuario", idUsuarioLoggeado);
        model.addAttribute("usuarioEsAdmin", esAdmin);
        model.addAttribute("nombreCompletoUsuario", nombreCompletoUsuarioLoggeado);

        Usuario cuentaNueva = new Usuario();
        Rol rol = new Rol();
        TipoIdentificacion tipoIdentificacion = new TipoIdentificacion();
        Estado estado = new Estado();

        cuentaNueva.setRol(rol);
        cuentaNueva.setTipoIdentificacion(tipoIdentificacion);
        cuentaNueva.setEstado(estado);

        model.addAttribute("cuentaNueva", cuentaNueva);
        model.addAttribute("listaTipoIdentificacion", tipoIdentificacionService.consultarTiposDeIdentificacion());
        model.addAttribute("listaRoles", rolService.consultarRolesDTO());
        model.addAttribute("listaEstados", estadoService.consultarEstadosUsuarios());
        return "registroUsuario";
    }

    @PostMapping("/cuenta-nueva")
    public String crearCuentaNueva(@Valid @ModelAttribute("cuentaNueva") Usuario usuario, BindingResult bindingResult, HttpSession session, Model model) {
        Long idUsuarioLoggeado = (Long)session.getAttribute("idUsuarioLoggeado");
        boolean esAdmin = (Long)session.getAttribute("idRolUsuarioLoggeado") == 2 ? true : false;
        String nombreCompletoUsuarioLoggeado = (String)session.getAttribute("nombreUsuarioLoggeado") + " " + (String)session.getAttribute("primerApellidoUsuarioLoggeado") + " " + (String)session.getAttribute("segundoApellidoUsuarioLoggeado");

        model.addAttribute("usuarioEsAdmin", esAdmin);
        model.addAttribute("nombreCompletoUsuario", nombreCompletoUsuarioLoggeado);

        if(bindingResult.hasErrors()) {
            model.addAttribute("cuentaNueva", usuario);
            model.addAttribute("listaTipoIdentificacion", tipoIdentificacionService.consultarTiposDeIdentificacion());
            model.addAttribute("listaRoles", rolService.consultarRolesDTO());
            model.addAttribute("listaEstados", estadoService.consultarEstadosUsuarios());
            return "registroUsuario";
        }

        boolean cuentaCreada = usuarioService.crearCuentaPorAdmin(usuario, idUsuarioLoggeado);
        session.setAttribute("mostrarNotificacion", true);
        session.setAttribute("origen", "cuentaNueva");

        if (cuentaCreada) {
            session.setAttribute("tipoNotificacion", "success");
            session.setAttribute("titulo", "¡Cuenta creada!");
            session.setAttribute("detalle", "Cuenta creada correctamente");
        }else {
            session.setAttribute("tipoNotificacion", "warning");
            session.setAttribute("titulo", "¡Cuenta no creada!");
            session.setAttribute("detalle", "Ocurrió un problema, la cuenta no fue creada. Inténtelo nuevamente.");
            session.setAttribute("origen", "cuentaNueva");
        }
        return "redirect:/cuenta-nueva";
    }
}