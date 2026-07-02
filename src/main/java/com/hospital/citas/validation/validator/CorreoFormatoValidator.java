package com.hospital.citas.validation.validator;

import com.hospital.citas.validation.annotation.CorreoFormato;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CorreoFormatoValidator implements ConstraintValidator<CorreoFormato, String> {
    @Override
    public boolean isValid(String correoPorEvaluar, ConstraintValidatorContext context) {
        if(correoPorEvaluar == null ||  correoPorEvaluar.isBlank()) {
            return true;
        }        
        return correoPorEvaluar.matches("^[\\w._-]+@[\\w.]+.[a-zA-Z]{2,4}$");
    }
}
