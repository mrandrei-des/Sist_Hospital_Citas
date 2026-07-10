package com.hospital.citas.service;
import java.util.List;

import org.springframework.stereotype.Service;

import com.hospital.citas.model.entity.DiaDeLaSemana;
import com.hospital.citas.repository.DiaDeLaSemanaRepository;

@Service
public class DiasDeLaSemanaService {
    private final DiaDeLaSemanaRepository diasDeLaSemanaRepository;

    DiasDeLaSemanaService(DiaDeLaSemanaRepository diasDeLaSemanaRepository) {
        this.diasDeLaSemanaRepository = diasDeLaSemanaRepository;
    }

    public List<DiaDeLaSemana> consultarDiasHorarioMedicoPorId(Long idMedico) {
        return diasDeLaSemanaRepository.consultarDiasHorarioMedicoPorId(idMedico);
    }
}
