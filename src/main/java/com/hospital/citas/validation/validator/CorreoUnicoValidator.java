package com.hospital.citas.validation.validator;

import jakarta.validation.ConstraintValidatorContext;
import com.hospital.citas.model.entity.Usuario;
import com.hospital.citas.service.UsuarioService;
import com.hospital.citas.validation.annotation.CorreoUnico;
import jakarta.validation.ConstraintValidator;

// Validator que se encarga de validar que el correo sea único en el sistema al momento de registrarse como paciente.
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
