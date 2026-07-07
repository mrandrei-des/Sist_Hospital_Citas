package com.hospital.citas.validation.validator;

import jakarta.validation.ConstraintValidatorContext;
import com.hospital.citas.model.dto.UsuarioMiPerfilDTO;
import com.hospital.citas.service.UsuarioService;
import com.hospital.citas.validation.annotation.IdentificacionUnicaEdit;

import jakarta.validation.ConstraintValidator;

public class IdentificacionUnicaEditValidator implements ConstraintValidator<IdentificacionUnicaEdit, UsuarioMiPerfilDTO> {
    private final UsuarioService usuarioService;

    IdentificacionUnicaEditValidator(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Override
    public boolean isValid(UsuarioMiPerfilDTO usuario, ConstraintValidatorContext context) {
        if(usuario == null ||  usuario.getIdentificacion().isBlank()) {
            return true;
        }
        boolean existeRegistro = usuarioService.existePorCorreoYNoIdentificacion(usuario.getId(), usuario.getIdentificacion());
        if(existeRegistro) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                .addPropertyNode("identificacion")
                .addConstraintViolation();
        }
        return !existeRegistro;
    }
}