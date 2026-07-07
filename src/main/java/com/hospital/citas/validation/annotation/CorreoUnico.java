package com.hospital.citas.validation.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import com.hospital.citas.validation.validator.CorreoUnicoValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CorreoUnicoValidator.class)
public @interface CorreoUnico {
    String message() default "El correo ya existe en el sistema. Indique otro diferente.";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}