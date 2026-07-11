package com.hospital.citas.validation.annotation;

import jakarta.validation.Payload;

public @interface HoraFinValida {
    String message() default "¡La hora de fin no pueder ser menor a la hora de inicio!";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
