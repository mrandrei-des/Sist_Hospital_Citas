package com.hospital.citas.validation.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import com.hospital.citas.validation.validator.HoraFinValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

// Anotación que se usa en la validación de la hora fin del registro de atención del médico en la configuración de horarios.
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = HoraFinValidator.class)
public @interface HoraFinValida {
    String message() default "¡La hora de fin no pueder ser menor a la hora de inicio!";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
