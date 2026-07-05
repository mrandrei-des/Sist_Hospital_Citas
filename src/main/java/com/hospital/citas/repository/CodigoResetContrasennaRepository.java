package com.hospital.citas.repository;

import java.lang.StackWalker.Option;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.hospital.citas.model.entity.CodigoResetContrasenna;
import com.hospital.citas.model.entity.Usuario;
import jakarta.transaction.Transactional;

public interface CodigoResetContrasennaRepository extends JpaRepository<CodigoResetContrasenna, Long> {
    List<CodigoResetContrasenna> findAllByUsuario(Usuario usuario);
    Optional<CodigoResetContrasenna> findByCodigoGeneradoAndUsuario(String codigoGenerado, Usuario usuario);
    Optional<CodigoResetContrasenna> findByUsuario(Usuario usuario);

    @Transactional
    @Modifying
    @Query(value = "{call sp_EliminaCodigos_OTP_Antiguos(:idUsuario)}", nativeQuery = true)
    void eliminarCodigosAntiguosPorIDUsuario(
        @Param("idUsuario") Long idUsuario
    ); 

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

    @Transactional
    @Modifying
    @Query(value = "{call sp_EliminaCodigo_OTP_Usado(:codigoGenerado, :idUsuario)}", nativeQuery = true)
    void eliminarCodigoSeguridad_Usado(
        @Param("codigoGenerado") String codigoGenerado,
        @Param("idUsuario") Long idUsuario
    );     

    @Transactional
    @Modifying
    @Query(value = "{call sp_InsertaCodigo_OTP_Generado(:codigoGenerado, :idUsuario)}", nativeQuery = true)
    void insertaRegistroBitacoraCodigoOTP_Generado(
        @Param("codigoGenerado") String codigoGenerado,
        @Param("idUsuario") Long idUsuario
    );         
}
