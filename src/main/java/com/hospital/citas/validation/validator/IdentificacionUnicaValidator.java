package com.hospital.citas.validation.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hospital.citas.model.entity.Usuario;
import com.hospital.citas.service.UsuarioService;
import com.hospital.citas.validation.annotation.IdentificacionUnica;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@Component
public class IdentificacionUnicaValidator implements ConstraintValidator<IdentificacionUnica, String> {

    @Autowired
    private UsuarioService usuarioService;

    @Override
    public boolean isValid(String identificacionPorEvaluar, ConstraintValidatorContext context) {
        if(identificacionPorEvaluar == null ||  identificacionPorEvaluar.isBlank()) {
            return true;
        }
        Usuario usuarioEncontrado = usuarioService.buscarPorIdentificacion(identificacionPorEvaluar);
        return usuarioEncontrado == null;
    }
}