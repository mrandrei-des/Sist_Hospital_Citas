package com.hospital.citas.repository;

import java.time.LocalTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.hospital.citas.model.dto.HorarioMedicoVistaDTO;
import com.hospital.citas.model.entity.DisponibilidadMedico;
import jakarta.transaction.Transactional;

public interface DisponibilidadMedicoRepository extends JpaRepository<DisponibilidadMedico, Long> {
    @Transactional
    @Modifying
    @Query(value = "{call sp_Inserta_Registro_Bitacora_Cambios_Horarios_Medicos(:idAccion, :idDisponibilidadAfectada, :descripcionAccion, :idUsuarioRealizoAccion)}", nativeQuery = true)
    void insertaRegistroBitacoraCambios(
        @Param("idAccion") Long idAccion,
        @Param("idDisponibilidadAfectada") Long idDisponibilidadAfectada,
        @Param("descripcionAccion") String descripcionAccion,
        @Param("idUsuarioRealizoAccion") Long idUsuarioRealizoAccion
    );

    @Query(value = "{call sp_ConsultaHorarioMedicoPorIdDia(:idMedico, :idDia)}", nativeQuery = true)
    List<HorarioMedicoVistaDTO> consultarHorarioMedicoPorIdDia(
        @Param("idMedico") Long idMedico, 
        @Param("idDia") Long idDia
    );

    @Query(value = "{call sp_ConsultaRegistrosHorarioMedico_HorasDentroRegistro(:p_idMedico, :p_idDia, :p_horaInicio, :p_horaFin)}", nativeQuery = true)
    <Optional>List<DisponibilidadMedico> consultaRegistrosHorarioMedico_HorasDentroRegistro(
        @Param("p_idMedico") Long idMedico, 
        @Param("p_idDia") Long idDia,
        @Param("p_horaInicio") LocalTime horaInicio, 
        @Param("p_horaFin") LocalTime horaFin
    );

    @Query(value = "{call sp_ConsultaRegistrosHorarioMedico_HorasAfueraRegistro(:p_idMedico, :p_idDia, :p_horaInicio, :p_horaFin)}", nativeQuery = true)
    <Optional>List<DisponibilidadMedico> consultaRegistrosHorarioMedico_HorasAfueraRegistro(
        @Param("p_idMedico") Long idMedico, 
        @Param("p_idDia") Long idDia,
        @Param("p_horaInicio") LocalTime horaInicio, 
        @Param("p_horaFin") LocalTime horaFin
    );
}