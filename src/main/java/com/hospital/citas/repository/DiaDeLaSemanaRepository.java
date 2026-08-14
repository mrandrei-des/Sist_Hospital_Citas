package com.hospital.citas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hospital.citas.model.entity.DiaDeLaSemana;

// Repository para la consulta de los días de la semana.
public interface DiaDeLaSemanaRepository extends JpaRepository<DiaDeLaSemana, Long> {
    @Query(value = "{call sp_ConsultaDiasHorarioMedicoPorId(:idMedico)}", nativeQuery = true)
    List<DiaDeLaSemana> consultarDiasHorarioMedicoPorId(@Param("idMedico") Long idMedico);

    @Query(value = "{call sp_ConsultaDiasSiguientesHorarioPorMedico(:idMedico, :idDiaInicio)}", nativeQuery = true)
    List<DiaDeLaSemana> consultarDiasSiguientesHorarioPorIdMedico(
        @Param("idMedico") Long idMedico,
        @Param("idDiaInicio") Long idDiaInicio
    );
}