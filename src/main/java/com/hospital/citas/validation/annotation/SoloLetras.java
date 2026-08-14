package com.hospital.citas.validation.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import com.hospital.citas.validation.validator.SoloLetrasValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

// Anotación que se usa en la validación de strings para solo permitir letras.
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SoloLetrasValidator.class)
public @interface SoloLetras {
    String message() default "No puede contener números ni carácteres especiales.";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}