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
        if(horario.getHoraInicio().isAfter(horario.getHoraFin())) horaInicioCorrecta = false;

        if(!horaInicioCorrecta) {
            context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                    .addPropertyNode("horaInicio")
                    .addConstraintViolation();
        }
        return horaInicioCorrecta;
    }
}
