package com.hospital.citas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import com.hospital.citas.model.dto.UsuarioMiPerfilDTO;
import com.hospital.citas.service.EstadoService;
import com.hospital.citas.service.MiPerfilService;
import com.hospital.citas.service.RolService;
import com.hospital.citas.service.TipoIdentificacionService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MiPerfilController {

    private final MiPerfilService miPerfilService;
    private final TipoIdentificacionService tipoIdentificacionService;
    private final RolService rolService;
    private final EstadoService estadoService;

    MiPerfilController(MiPerfilService miPerfilService, TipoIdentificacionService tipoIdentificacionService, RolService rolService, EstadoService estadoService) {
        this.miPerfilService = miPerfilService;
        this.tipoIdentificacionService = tipoIdentificacionService;
        this.rolService = rolService;
        this.estadoService = estadoService;
    }

    @GetMapping("/mi-perfil/{id}")
    public String mostrarPerfil(@PathVariable("id") Long id, HttpSession session, Model model) {

        Long idUsuarioLoggeado = (Long)session.getAttribute("idUsuarioLoggeado");
        boolean esAdmin = (Long)session.getAttribute("idRolUsuarioLoggeado") == 2 ? true : false;
        String nombreCompletoUsuarioLoggeado = (String)session.getAttribute("nombreUsuarioLoggeado") + " " + (String)session.getAttribute("primerApellidoUsuarioLoggeado") + " " + (String)session.getAttribute("segundoApellidoUsuarioLoggeado");

        boolean mostrarMensaje = (boolean)session.getAttribute("mostrarNotificacion");
        String origenNotificacion = (String)session.getAttribute("origen");
        
        if(mostrarMensaje && origenNotificacion.equals("miPerfil")) {
            model.addAttribute("mostrarNotificacion", true);
            model.addAttribute("tipoNotificacion", (String)session.getAttribute("tipoNotificacion"));
            model.addAttribute("titulo", (String)session.getAttribute("titulo"));
            model.addAttribute("detalle", (String)session.getAttribute("detalle"));

            session.setAttribute("mostrarNotificacion", false);
            session.setAttribute("origen", "");
        }

        model.addAttribute("usuarioEsAdmin", esAdmin);
        model.addAttribute("nombreCompletoUsuario", nombreCompletoUsuarioLoggeado);

        if(!esAdmin) {
            model.addAttribute("esAdminMant", false);
            model.addAttribute("esAdmin", false);
        }else {
            model.addAttribute("esAdmin", true);
            if(idUsuarioLoggeado.compareTo(id) != 0) 
                model.addAttribute("esAdminMant", true);
            else 
                model.addAttribute("esAdminMant", false);
        }

        model.addAttribute("idRolUsuario", session.getAttribute("idUsuarioLoggeado"));
        model.addAttribute("usuario", miPerfilService.buscarPorId(id));
        model.addAttribute("listaTipoIdentificacion", tipoIdentificacionService.consultarTiposDeIdentificacion());
        model.addAttribute("listaRoles", rolService.consultarRolesDTO());
        model.addAttribute("listaEstados", estadoService.consultarEstadosUsuarios());
        return "miPerfil";
    }

    @PostMapping("/actualizar-mi-cuenta")
    public String actualizarCuenta(@Valid @ModelAttribute("usuario") UsuarioMiPerfilDTO usuario, BindingResult bindingResult, HttpSession session, Model model) {
        
        Long idUsuarioLoggeado = (Long)session.getAttribute("idUsuarioLoggeado");
        boolean esAdmin = (Long)session.getAttribute("idRolUsuarioLoggeado") == 2 ? true : false;
        boolean esAdminMant = false;

        String nombreCompletoUsuarioLoggeado = (String)session.getAttribute("nombreUsuarioLoggeado") + " " + (String)session.getAttribute("primerApellidoUsuarioLoggeado") + " " + (String)session.getAttribute("segundoApellidoUsuarioLoggeado");

        model.addAttribute("usuarioEsAdmin", esAdmin);
        model.addAttribute("nombreCompletoUsuario", nombreCompletoUsuarioLoggeado);

        if(!esAdmin) {
            model.addAttribute("esAdminMant", false);
            model.addAttribute("esAdmin", false);
        }else {
            model.addAttribute("esAdmin", true);
            if(idUsuarioLoggeado.compareTo(usuario.getId()) != 0)  {
                model.addAttribute("esAdminMant", true);
                esAdminMant = true;
            }
            else 
                model.addAttribute("esAdminMant", false);
        }

        model.addAttribute("usuarioEsAdmin", esAdmin);
        model.addAttribute("idRolUsuario", session.getAttribute("idUsuarioLoggeado"));
        // model.addAttribute("esAdminMant", false);

        if(bindingResult.hasErrors()) {
            model.addAttribute("usuario", usuario);
            model.addAttribute("listaTipoIdentificacion", tipoIdentificacionService.consultarTiposDeIdentificacion());
            model.addAttribute("listaRoles", rolService.consultarRolesDTO());
            model.addAttribute("listaEstados", estadoService.consultarEstadosUsuarios());
            return "miPerfil";
        }

        session.setAttribute("mostrarNotificacion", true);
        if(miPerfilService.actualizarPerfil(usuario, idUsuarioLoggeado)) {
            model.addAttribute("usuario", miPerfilService.buscarPorId(usuario.getId()));
            session.setAttribute("tipoNotificacion", "success");
            session.setAttribute("titulo", "¡Perfil actualizado!");
            session.setAttribute("detalle", "Perfil actualizado correctamente");
            session.setAttribute("origen", "miPerfil");
        }else {
            model.addAttribute("usuario", usuario);
            session.setAttribute("tipoNotificacion", "warning");
            session.setAttribute("titulo", "¡Perfil no procesado!");
            session.setAttribute("detalle", "Ocurrió un problema, perfil no fue actualizado. Inténtelo nuevamente.");
            session.setAttribute("origen", "miPerfil");
        }

        model.addAttribute("listaTipoIdentificacion", tipoIdentificacionService.consultarTiposDeIdentificacion());
        model.addAttribute("listaRoles", rolService.consultarRolesDTO());
        model.addAttribute("listaEstados", estadoService.consultarEstadosUsuarios());

        // MOSTRAR NOTIFICACION
        if(esAdminMant) return "redirect:/mostrar-panel-pacientes";

        return "miPerfil";
    }
}