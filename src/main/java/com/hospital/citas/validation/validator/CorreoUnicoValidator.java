package com.hospital.citas.validation.validator;

import jakarta.validation.ConstraintValidatorContext;
import com.hospital.citas.model.entity.Usuario;
import com.hospital.citas.service.UsuarioService;
import com.hospital.citas.validation.annotation.CorreoUnico;
import jakarta.validation.ConstraintValidator;

public class CorreoUnicoValidator implements ConstraintValidator<CorreoUnico, String> {

    private final UsuarioService usuarioService;

    CorreoUnicoValidator(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Override
    public boolean isValid(String correoPorEvaluar, ConstraintValidatorContext context) {

        if(correoPorEvaluar == null ||  correoPorEvaluar.isBlank()) {
            return true;
        }

        Usuario usuarioEncontrado = usuarioService.buscarPorCorreoElectronico(correoPorEvaluar);
        return usuarioEncontrado == null;
    }
}
