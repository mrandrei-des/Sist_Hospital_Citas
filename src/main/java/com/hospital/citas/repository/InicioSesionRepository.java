package com.hospital.citas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.hospital.citas.model.entity.Usuario;

// Repository para las consultas de inicio de sesión.
public interface InicioSesionRepository extends JpaRepository<Usuario, Long>  {

}
