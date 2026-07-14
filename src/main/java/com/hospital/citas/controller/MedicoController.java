package com.hospital.citas.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import com.hospital.citas.model.dto.MedicoDTO;
import com.hospital.citas.service.MedicoService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MedicoController {
    private final MedicoService medicoService;

    MedicoController(MedicoService medicoService) {
        this.medicoService = medicoService;
    }

    @GetMapping("/medicos")
    public String mostrarRegistroMedicos(HttpSession session, Model model) {

        boolean esAdmin = (Long)session.getAttribute("idRolUsuarioLoggeado") == 2 ? true : false;
        String nombreCompletoUsuarioLoggeado = (String)session.getAttribute("nombreUsuarioLoggeado") + " " + (String)session.getAttribute("primerApellidoUsuarioLoggeado") + " " + (String)session.getAttribute("segundoApellidoUsuarioLoggeado");

        boolean mostrarNotificacion = (boolean)session.getAttribute("mostrarNotificacion");
        String origenNotificacion = (String)session.getAttribute("origen");

        if(mostrarNotificacion && origenNotificacion.equals("medicos")) {
            model.addAttribute("mostrarNotificacion", true);
            model.addAttribute("tipoNotificacion", (String)session.getAttribute("tipoNotificacion"));
            model.addAttribute("titulo", (String)session.getAttribute("titulo"));
            model.addAttribute("detalle", (String)session.getAttribute("detalle"));

            session.setAttribute("mostrarNotificacion", false);
            session.setAttribute("origen", "");
        }

        model.addAttribute("nombreCompletoUsuario", nombreCompletoUsuarioLoggeado);
        model.addAttribute("usuarioEsAdmin", esAdmin);
        model.addAttribute("idRolUsuario", session.getAttribute("idUsuarioLoggeado"));

        model.addAttribute("medico", new MedicoDTO());
        model.addAttribute("listaMedicos", medicoService.listaMedicoRegistradoDTOs());
        model.addAttribute("listaEspecialidades", medicoService.listaEspecialidadesDTO());
        return "registroMedicos";
    }

    @PostMapping("/registro-medico")
    public String registroMedico(@Valid @ModelAttribute("medico") MedicoDTO medico, BindingResult bindingResult, HttpSession session, Model model) {
        
        Long idUsuarioLoggeado = (Long)session.getAttribute("idUsuarioLoggeado");
        boolean esAdmin = (Long)session.getAttribute("idRolUsuarioLoggeado") == 2 ? true : false;
        String nombreCompletoUsuarioLoggeado = (String)session.getAttribute("nombreUsuarioLoggeado") + " " + (String)session.getAttribute("primerApellidoUsuarioLoggeado") + " " + (String)session.getAttribute("segundoApellidoUsuarioLoggeado");

        model.addAttribute("nombreCompletoUsuario", nombreCompletoUsuarioLoggeado);
        model.addAttribute("usuarioEsAdmin", esAdmin);
        model.addAttribute("idRolUsuario", session.getAttribute("idUsuarioLoggeado"));

        if(bindingResult.hasErrors()) {
            model.addAttribute("medico", medico);
            model.addAttribute("listaMedicos", medicoService.listaMedicoRegistradoDTOs());
            model.addAttribute("listaEspecialidades", medicoService.listaEspecialidadesDTO());
            return "registroMedicos";
        }

        boolean medicoProcesado = medicoService.procesarMedico(medico, idUsuarioLoggeado);
        session.setAttribute("mostrarNotificacion", true);
        if(medicoProcesado) {
            model.addAttribute("medico", new MedicoDTO());            
            session.setAttribute("tipoNotificacion", "success");
            session.setAttribute("titulo", "¡Médico procesado!");
            session.setAttribute("detalle", "Médico procesado correctamente");
            session.setAttribute("origen", "medicos");
        }else {
            model.addAttribute("medico", medico);
            session.setAttribute("tipoNotificacion", "success");
            session.setAttribute("titulo", "¡Médico no procesado!");
            session.setAttribute("detalle", "Ocurrió un problema, el médico no ha sido procesado. Inténtelo nuevamente.");
            session.setAttribute("origen", "medicos");
        }

        model.addAttribute("listaMedicos", medicoService.listaMedicoRegistradoDTOs());
        model.addAttribute("listaEspecialidades", medicoService.listaEspecialidadesDTO());
        return medicoProcesado ? "redirect:/medicos" : "registroMedicos";
    }

    @GetMapping("/buscar-medico/{id}")
    public String buscarMedicoPorId(@PathVariable("id") Long idMedico, HttpSession session, Model model) {

        boolean esAdmin = (Long)session.getAttribute("idRolUsuarioLoggeado") == 2 ? true : false;
        model.addAttribute("usuarioEsAdmin", esAdmin);
        model.addAttribute("idRolUsuario", session.getAttribute("idUsuarioLoggeado"));

        model.addAttribute("medico", medicoService.buscarPorId(idMedico));
        model.addAttribute("listaMedicos", medicoService.listaMedicoRegistradoDTOs());
        model.addAttribute("listaEspecialidades", medicoService.listaEspecialidadesDTO());
        return "registroMedicos";
    }
    
    @GetMapping("/deshabilitar-medico/{id}")
    public String deshabilitarMedico(@PathVariable("id") Long idMedico, HttpSession session, Model model) {
        
        Long idUsuarioLoggeado = (Long)session.getAttribute("idUsuarioLoggeado");
        boolean esAdmin = (Long)session.getAttribute("idRolUsuarioLoggeado") == 2 ? true : false;
        model.addAttribute("usuarioEsAdmin", esAdmin);
        model.addAttribute("idRolUsuario", session.getAttribute("idUsuarioLoggeado"));

        boolean eliminacionCompletada = medicoService.eliminarPorId(idMedico, idUsuarioLoggeado);
        model.addAttribute("medico", new MedicoDTO());
        model.addAttribute("listaMedicos", medicoService.listaMedicoRegistradoDTOs());
        model.addAttribute("listaEspecialidades", medicoService.listaEspecialidadesDTO());
        session.setAttribute("mostrarNotificacion", true);

        if(eliminacionCompletada) {      
            session.setAttribute("tipoNotificacion", "success");
            session.setAttribute("titulo", "¡Médico eliminado!");
            session.setAttribute("detalle", "Médico procesado correctamente");
            session.setAttribute("origen", "medicos");
        }else {
            session.setAttribute("tipoNotificacion", "warning");
            session.setAttribute("titulo", "¡Médico no eliminado!");
            session.setAttribute("detalle", "Ocurrió un problema, el médico no ha sido eliminado. Inténtelo nuevamente.");
            session.setAttribute("origen", "medicos");
        }

        return "redirect:/medicos";
    }

    @GetMapping("/api/medicos/consulta/por-especialidad/{id}")
    public ResponseEntity<List<MedicoDTO>> obtenerMedicosPorEspecialidad(@PathVariable Long id) {
        List<MedicoDTO> listaMedicoDTOs = medicoService.listarMedicosPorEspecialidad(id);
        return ResponseEntity.ok(listaMedicoDTOs);
    }
}