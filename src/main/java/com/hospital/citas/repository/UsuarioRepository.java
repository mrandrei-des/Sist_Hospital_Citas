package com.hospital.citas.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.hospital.citas.model.entity.Usuario;
import jakarta.transaction.Transactional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario>findByCorreoElectronico(String correoElectronicoBuscar);
    Optional<Usuario>findByIdentificacion(String identificacionBuscar);

    @Transactional
    @Modifying
    @Query(value = "{call sp_Inserta_Registro_Bitacora_Cambios_Usuarios(:idAccion, :idUsuarioAfectado, :descripcionAccion, :idUsuarioRealizoAccion)}", nativeQuery = true)
    void insertaRegistroBitacoraCambiosUsuario(
        @Param("idAccion") Long idAccion,
        @Param("idUsuarioAfectado") Long idUsuarioAfectado,
        @Param("descripcionAccion") String descripcionAccion,
        @Param("idUsuarioRealizoAccion") Long idUsuarioRealizoAccion
    );    
}
