package com.hospital.citas.repository;

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

    @Query(value = "{call sp_ConsultaHorarioMedicoPorId(:idMedico)}", nativeQuery = true)
    List<HorarioMedicoVistaDTO> consultarHorarioMedicoPorId(@Param("idMedico") Long id);
}
