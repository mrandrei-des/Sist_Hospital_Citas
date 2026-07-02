package com.hospital.citas.validation.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import com.hospital.citas.validation.validator.PasswordSeguroValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordSeguroValidator.class)
public @interface PasswordSeguro {
    String message() default "Mínimo 5 caracteres Máximo 10. Al menos una letra mayúscula, una letra minúscula, un número y un carácter especial.";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
