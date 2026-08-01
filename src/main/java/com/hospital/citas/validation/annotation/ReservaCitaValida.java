package com.hospital.citas.validation.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.hospital.citas.validation.validator.ReservaCitaValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ReservaCitaValidator.class)
public @interface ReservaCitaValida {
    String message() default "¡La cita a reservar no está disponible!";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
