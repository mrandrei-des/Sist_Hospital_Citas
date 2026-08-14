package com.hospital.citas.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hospital.citas.model.entity.Estado;
import com.hospital.citas.repository.EstadoRepository;

// Servicio que consulta los estados de los registros del sistema.
@Service
public class EstadoService {

    private final EstadoRepository estadoRepository;

    EstadoService(EstadoRepository estadoRepository) {
        this.estadoRepository = estadoRepository;
    }

    // Consulta los estados de activo e inactivo.
    // Usado al cargar el select de estado en el mantenimiento de pacientes.
    public List<Estado> consultarEstadosUsuarios(){
        List<Integer> listaOpcionesEstado = List.of(3, 4);
        return estadoRepository.findByIdIn(listaOpcionesEstado);
    }

    // Consulta los estados de pendiente, confirmado y cancelado.
    // Usado al establecerle el estado en el renderizado de las citas médicas.
    public List<Estado> consultarEstadosCitas(){
        List<Integer> listaOpcionesEstado = List.of(1, 2, 7);
        return estadoRepository.findByIdIn(listaOpcionesEstado);
    }

}
