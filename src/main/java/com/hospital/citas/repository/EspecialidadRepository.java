package com.hospital.citas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hospital.citas.model.dto.UltimaEspecialidadRegistradaDTO;
import com.hospital.citas.model.entity.Especialidad;
import com.hospital.citas.model.entity.Estado;

import jakarta.transaction.Transactional;

public interface EspecialidadRepository extends JpaRepository<Especialidad, Long> {
    List<Especialidad>findAllByEstado(Estado estado);

    @Transactional
    @Modifying
    @Query(value = "{call sp_Inserta_Registro_Bitacora_Cambios_Especialidades(:idAccion, :idEspecialidadAfectada, :descripcionAccion, :idUsuarioRealizoAccion)}", nativeQuery = true)
    void insertaRegistroBitacoraCambios(
        @Param("idAccion") Long idAccion,
        @Param("idEspecialidadAfectada") Long idEspecialidadAfectada,
        @Param("descripcionAccion") String descripcionAccion,
        @Param("idUsuarioRealizoAccion") Long idUsuarioRealizoAccion
    );

    @Query(value = "{call sp_listarUltimasEspecialidadesRegistradas()}", nativeQuery = true)
    List<UltimaEspecialidadRegistradaDTO> listaUltimasEspecialidadRegistradasDtos();

    @Query(value = "{call sp_ConsultaEspecialidadesConMedicos(:p_estado)}", nativeQuery = true)
    List<Especialidad> consultarEspecialidadesConMedico(@Param("p_estado") Long idEstado);
}
