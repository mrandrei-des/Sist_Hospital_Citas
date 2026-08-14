package com.hospital.citas.validation.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.hospital.citas.validation.validator.CorreoFormatoValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

// Anotación que se usa en la validación del formato del correo al registrarse en el sistema desde fuera.
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CorreoFormatoValidator.class)
public @interface CorreoFormato {
    String message() default "El correo no tiene un formato válido.";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}