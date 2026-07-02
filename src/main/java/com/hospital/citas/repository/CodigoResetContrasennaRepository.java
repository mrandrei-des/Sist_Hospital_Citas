package com.hospital.citas.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.hospital.citas.model.entity.CodigoResetContrasenna;
import com.hospital.citas.model.entity.Usuario;
import jakarta.transaction.Transactional;

public interface CodigoResetContrasennaRepository extends JpaRepository<CodigoResetContrasenna, Long> {
    List<CodigoResetContrasenna> findAllByUsuario(Usuario usuario);
    void deleteByUsuario(Usuario usuario);

    @Transactional
    @Modifying
    @Query(value = "{call sp_InsertaCodigo_OTP_Expirado(:codigoGenerado, :idUsuario, :fechaExpiro)}", nativeQuery = true)
    void insertaRegistroBitacoraCodigoOTP_Expirado(
        @Param("codigoGenerado") String codigoGenerado,
        @Param("idUsuario") Long idUsuario,
        @Param("fechaExpiro") LocalDateTime fechaExpiro
    ); 

    @Transactional
    @Modifying
    @Query(value = "{call sp_InsertaCodigo_OTP_Usado(:codigoGenerado, :idUsuario)}", nativeQuery = true)
    void insertaRegistroBitacoraCodigoOTP_Usado(
        @Param("codigoGenerado") String codigoGenerado,
        @Param("idUsuario") Long idUsuario
    );     
}
