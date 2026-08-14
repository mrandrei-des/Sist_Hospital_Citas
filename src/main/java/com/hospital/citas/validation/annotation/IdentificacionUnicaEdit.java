package com.hospital.citas.validation.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import com.hospital.citas.validation.validator.IdentificacionUnicaEditValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

// Anotación que se usa en la validación de única existencia de la identificación en el sistema. Usado en el mantenimiento de usuarios y en mi perfil.
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = IdentificacionUnicaEditValidator.class)
public @interface IdentificacionUnicaEdit {
    String message() default "La identificación ya existe en el sistema. Indique otra diferente.";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}