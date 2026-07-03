package com.hospital.citas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InicioController {
    @GetMapping("/inicio")
    public String inicio(){
        return "inicio";
    }

    @GetMapping("/acceso-denegado")
    public String accesoDenegado(){
        return "acceso-denegado";
    }
}
