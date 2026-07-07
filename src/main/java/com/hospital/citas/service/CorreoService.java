package com.hospital.citas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class CorreoService {
    @Autowired
    private JavaMailSender mailSender;

    @Async
    public void enviarCorreoCodigo(String correoDestino, String codigo) {
        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setTo(correoDestino);
        mensaje.setSubject("Código de Recuperación - Sistema Hospitalario MR");
        mensaje.setText("Estimado usuario,\n\n"
                + "Su código de seguridad para restablecer la contraseña es: " + codigo + "\n"
                + "Este código es válido por 15 minutos.\n\n"
                + "Si usted no solicitó este cambio, ignore este correo.");
        mailSender.send(mensaje);
    }
}
