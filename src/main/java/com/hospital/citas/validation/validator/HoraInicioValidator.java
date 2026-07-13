package com.hospital.citas.validation.validator;

import com.hospital.citas.model.dto.HorarioMedicoDTO;
import com.hospital.citas.validation.annotation.HoraInicioValida;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class HoraInicioValidator implements ConstraintValidator<HoraInicioValida, HorarioMedicoDTO> {
    @Override
    public boolean isValid(HorarioMedicoDTO horario, ConstraintValidatorContext context) {

        if(horario == null || (horario.getHoraInicio() == null || horario.getHoraFin() == null)) {
            return true;
        }

        boolean horaInicioCorrecta = true;
        String mensajeValidacion = "";

        if(horario.getHoraInicio().isAfter(horario.getHoraFin())) {
            horaInicioCorrecta = false;
            mensajeValidacion = "¡La hora de inicio no pueder ser mayor a la hora fin!";
        }
        
        if(horario.getHoraFin().equals(horario.getHoraInicio())) {
            horaInicioCorrecta = false;
            mensajeValidacion = "¡La hora de inicio debe ser inferior a la hora de fin!";
        }

        if(!horaInicioCorrecta) {
            context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(mensajeValidacion)
                    .addPropertyNode("horaInicio")
                    .addConstraintViolation();
        }
        return horaInicioCorrecta;
    }
}
