package com.hospital.citas.validation.validator;

import jakarta.validation.ConstraintValidatorContext;

import com.hospital.citas.model.dto.UsuarioMiPerfilDTO;
import com.hospital.citas.service.UsuarioService;
import com.hospital.citas.validation.annotation.CorreoUnicoEdit;

import jakarta.validation.ConstraintValidator;

// Validator que se encarga de validar que el correo solo exista una vez en el sistema, usado al modificar un usuario y en mi perfil.
public class CorreoUnicoEditValidator implements ConstraintValidator<CorreoUnicoEdit, UsuarioMiPerfilDTO> {
    private final UsuarioService usuarioService;

    CorreoUnicoEditValidator(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Override
    public boolean isValid(UsuarioMiPerfilDTO usuario, ConstraintValidatorContext context) {
        if(usuario == null ||  usuario.getCorreoElectronico().isBlank()) {
            return true;
        }

        boolean existeRegistro = usuarioService.existePorCorreoYNoId(usuario.getId(), usuario.getCorreoElectronico());
        if(existeRegistro) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                .addPropertyNode("correoElectronico")
                .addConstraintViolation();
        }

        return !existeRegistro;
    }
}