package com.hospital.citas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.hospital.citas.model.entity.Usuario;

public interface InicioSesionRepository extends JpaRepository<Usuario, Long>  {

}
