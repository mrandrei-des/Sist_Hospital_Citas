package com.hospital.citas.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hospital.citas.model.entity.Estado;
import com.hospital.citas.repository.EstadoRepository;

@Service
public class EstadoService {

    private final EstadoRepository estadoRepository;

    EstadoService(EstadoRepository estadoRepository) {
        this.estadoRepository = estadoRepository;
    }

    public List<Estado> consultarEstadosUsuarios(){
        List<Integer> listaOpcionesEstado = List.of(3, 4);
        return estadoRepository.findByIdIn(listaOpcionesEstado);
    }

    public List<Estado> consultarEstadosCitas(){
        List<Integer> listaOpcionesEstado = List.of(1, 2, 7);
        return estadoRepository.findByIdIn(listaOpcionesEstado);
    }

}
