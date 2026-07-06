package com.hospital.citas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hospital.citas.model.dto.MedicoRegistradoDTO;
import com.hospital.citas.model.entity.Estado;
import com.hospital.citas.model.entity.Medico;

import jakarta.transaction.Transactional;

public interface MedicoRepository extends JpaRepository<Medico, Long> {
    List<Medico> findAllByEstado(Estado estado);

    @Transactional
    @Modifying
    @Query(value = "{call sp_Inserta_Registro_Bitacora_Cambios_Medicos(:idAccion, :idMedicoAfectado, :descripcionAccion, :idUsuarioRealizoAccion)}", nativeQuery = true)
    void insertaRegistroBitacoraCambios(
        @Param("idAccion") Long idAccion,
        @Param("idMedicoAfectado") Long idMedicoAfectado,
        @Param("descripcionAccion") String descripcionAccion,
        @Param("idUsuarioRealizoAccion") Long idUsuarioRealizoAccion
    );

    @Query(value = "{call sp_listarMedicosRegistrados()}", nativeQuery = true)
    List<MedicoRegistradoDTO> listaMedicosRegistradoMedicoRegistradoDTOs();

    @Transactional
    @Modifying
    @Query(value = "{call sp_EliminarMedico(:idMedico)}", nativeQuery = true)
    void eliminarMedico(
        @Param("idMedico") Long idAccion
    );
}
