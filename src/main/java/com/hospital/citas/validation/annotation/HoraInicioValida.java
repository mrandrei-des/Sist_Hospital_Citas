package com.hospital.citas.validation.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import com.hospital.citas.validation.validator.HoraInicioValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

// Anotación que se usa en la validación de la hora inicio del registro de atención del médico en la configuración de horarios.
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = HoraInicioValidator.class)
public @interface HoraInicioValida {
    String message() default "¡La hora de inicio no pueder ser mayor a la hora fin!";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
