package com.hospital.citas.service;
import java.util.List;

import org.springframework.stereotype.Service;

import com.hospital.citas.model.entity.DiaDeLaSemana;
import com.hospital.citas.repository.DiaDeLaSemanaRepository;

// Servicio que consulta los días de la semana y días de atención de los horarios de los médicos.
@Service
public class DiasDeLaSemanaService {
    private final DiaDeLaSemanaRepository diasDeLaSemanaRepository;

    DiasDeLaSemanaService(DiaDeLaSemanaRepository diasDeLaSemanaRepository) {
        this.diasDeLaSemanaRepository = diasDeLaSemanaRepository;
    }

    // Consulta todos los días de la semana que el médico indicado tiene horario de atención registrado.
    // Utilizado al momento de renderizar y preparar el horario de atención completo del médico indicado.
    public List<DiaDeLaSemana> consultarDiasHorarioMedicoPorId(Long idMedico) {
        return diasDeLaSemanaRepository.consultarDiasHorarioMedicoPorId(idMedico);
    }

    // Del horario de atención registrado del médico indicado, consulta los días siguientes al día indicado.
    // Usado al momento de renderizar el horario de disponibilidad del médico.
    public List<DiaDeLaSemana> consultarDiasSiguientesHorarioPorIdMedico(Long idMedico, Long idDiaInicio) {
        return diasDeLaSemanaRepository.consultarDiasSiguientesHorarioPorIdMedico(idMedico, idDiaInicio);
    }
}
