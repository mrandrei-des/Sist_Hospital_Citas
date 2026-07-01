package com.hospital.citas.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hospital.citas.model.entity.TipoIdentificacion;
import com.hospital.citas.repository.TipoIdentificacionRepository;

@Service
public class TipoIdentificacionService {
    private final TipoIdentificacionRepository tipoIdentificacionRepository;

    TipoIdentificacionService(TipoIdentificacionRepository tipoIdentificacionRepository) {
        this.tipoIdentificacionRepository = tipoIdentificacionRepository;
    }

    public List<TipoIdentificacion> consultarTiposDeIdentificacion() {
        return tipoIdentificacionRepository.findAll();
    }
}