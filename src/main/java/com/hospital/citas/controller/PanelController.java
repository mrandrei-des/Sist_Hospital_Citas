package com.hospital.citas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PanelController {
    @GetMapping("/mostrarPanel")
    public String cargarPanelAdministrador(Model model) {
        return "panel";
    }
}
