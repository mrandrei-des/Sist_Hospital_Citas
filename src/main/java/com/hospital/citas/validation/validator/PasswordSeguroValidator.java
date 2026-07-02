package com.hospital.citas.validation.validator;

import com.hospital.citas.validation.annotation.PasswordSeguro;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordSeguroValidator implements ConstraintValidator<PasswordSeguro, String>  {
    @Override
    public boolean isValid(String passwordPorEvaluar, ConstraintValidatorContext context) {
        if(passwordPorEvaluar == null ||  passwordPorEvaluar.isBlank()) {
            return true;
        }        
        return passwordPorEvaluar.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*0-9)(?=.*[@$!%.*?&])[A-Za-z0-9@$!%.*?&]{5,10}$");
    }
}
