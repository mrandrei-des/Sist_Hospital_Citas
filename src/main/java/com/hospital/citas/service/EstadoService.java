package com.hospital.citas.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hospital.citas.model.entity.Estado;
import com.hospital.citas.repository.EstadoRepository;

@Service
public class EstadoService {

    @Autowired
    private EstadoRepository estadoRepository;

    public List<Estado> consultarEstadosUsuarios(){
        List<Integer> listaOpcionesEstado = List.of(3, 4);
        return estadoRepository.findByIdIn(listaOpcionesEstado);
    }
}
