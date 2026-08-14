package com.hospital.citas.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hospital.citas.model.entity.TipoIdentificacion;
import com.hospital.citas.repository.TipoIdentificacionRepository;

// Servicio que consulta los tipos de identificación permitidos en el sistema.
@Service
public class TipoIdentificacionService {
    private final TipoIdentificacionRepository tipoIdentificacionRepository;

    TipoIdentificacionService(TipoIdentificacionRepository tipoIdentificacionRepository) {
        this.tipoIdentificacionRepository = tipoIdentificacionRepository;
    }

    // Consulta todos los tipos de identificación registrados en el sistema.
    public List<TipoIdentificacion> consultarTiposDeIdentificacion() {
        return tipoIdentificacionRepository.findAll();
    }
}