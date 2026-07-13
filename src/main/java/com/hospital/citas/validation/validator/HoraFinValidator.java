package com.hospital.citas.validation.validator;

import com.hospital.citas.model.dto.HorarioMedicoDTO;
import com.hospital.citas.validation.annotation.HoraFinValida;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class HoraFinValidator implements ConstraintValidator<HoraFinValida, HorarioMedicoDTO> {
    
    @Override
    public boolean isValid(HorarioMedicoDTO horario, ConstraintValidatorContext context) {
        
        if(horario == null || (horario.getHoraInicio() == null || horario.getHoraFin() == null)) {
            return true;
        }

        boolean horaFinCorrecta = true;
        String mensajeValidacion = "";

        if(horario.getHoraFin().isBefore(horario.getHoraInicio())) {
            horaFinCorrecta = false;
            mensajeValidacion = "¡La hora de fin no pueder ser menor a la hora de inicio!";
        }
        
        if(horario.getHoraFin().equals(horario.getHoraInicio())) {
            horaFinCorrecta = false;
            mensajeValidacion = "¡La hora de fin debe ser superior a la hora de inicio!";
        }

        if(!horaFinCorrecta) {
            context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(mensajeValidacion)
                    .addPropertyNode("horaFin")
                    .addConstraintViolation();
        }
        return horaFinCorrecta;
    }
}
