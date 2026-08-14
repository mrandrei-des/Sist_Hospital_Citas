package com.hospital.citas.validation.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.hospital.citas.validation.validator.CorreoUnicoEditValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

// Anotación que se usa en la validación del correo único en el sistema, usado en el mantenimiento de usuarios y en mi perfil.
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CorreoUnicoEditValidator.class)
public @interface CorreoUnicoEdit {
    String message() default "El correo ya existe en el sistema. Indique otro diferente.";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}