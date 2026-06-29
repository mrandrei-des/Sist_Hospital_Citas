package com.hospital.citas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.hospital.citas.model.entity.Usuario;

@Controller
public class InicioSesionController {
    @GetMapping("/")
    public String cargarInicioSesion(Model model){
        model.addAttribute("usuario", new Usuario());
        return "inicioSesion";
    }
}
