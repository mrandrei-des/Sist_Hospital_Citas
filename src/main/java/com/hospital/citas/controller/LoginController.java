package com.hospital.citas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// ESTE CONTROLER VA A TENER A CARGO EL INICIO DE SESIÓN EN EL SISTEMA
@Controller
public class LoginController {
    @GetMapping("/login")
    public String login() {
        return "inicioSesion";
    }
}
