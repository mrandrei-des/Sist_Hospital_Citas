package com.hospital.citas.validation.validator;

import com.hospital.citas.validation.annotation.SoloLetras;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

// Validator que un string cumpla con que todo sus carácteres sean únicamente letras.
public class SoloLetrasValidator implements ConstraintValidator<SoloLetras, String> {
    @Override
    public boolean isValid(String cadenaPorEvaluar, ConstraintValidatorContext context) {
        if(cadenaPorEvaluar == null ||  cadenaPorEvaluar.isBlank()) {
            return true;
        }        
        return cadenaPorEvaluar.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$");
    }
}
