package com.hospital.citas.validation.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.hospital.citas.validation.validator.IdentificacionUnicaValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = IdentificacionUnicaValidator.class)
public @interface IdentificacionUnica {
        String message() default "la identificación ya existe en el sistema. Indique otra diferente.";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
