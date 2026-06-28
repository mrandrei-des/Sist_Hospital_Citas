package com.hospital.citas.controller;

import org.springframework.web.bind.annotation.GetMapping;

public class index {
    
    @GetMapping("/")
    public String bienvenida(){
        return "/";
    }
}
