package com.hospital.citas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hospital.citas.model.dto.ReservaCitasReservaDTO;
import com.hospital.citas.model.entity.CodigoResetContrasenna;
import com.hospital.citas.service.ReservaCitasService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequestMapping("/reserva-citas")
public class ReservaCitasController {
    private final ReservaCitasService reservaCitasService;

    ReservaCitasController(ReservaCitasService reservaCitasService) {
        this.reservaCitasService = reservaCitasService;
    }

    @PostMapping("/nueva")
    public String nuevaReserva(@Valid @ModelAttribute("reserva") ReservaCitasReservaDTO reserva, BindingResult bindingResult, HttpSession session, Model model) {

        // RECIBIR EL OBJETO QUE VIENE DEL FORMULARIO
        Long idUsuarioLoggeado = (Long)session.getAttribute("idUsuarioLoggeado");

        // VALIDAR LOS DATOS
        if(bindingResult.hasErrors()) {
            model.addAttribute("reserva", reserva);
            return "mostrar-reserva";
        }
        // revisar que el espacio esté disponible
            // GUARDAR LA RESERVA
        return "redirect:/mostrar-reserva";
    }
}
