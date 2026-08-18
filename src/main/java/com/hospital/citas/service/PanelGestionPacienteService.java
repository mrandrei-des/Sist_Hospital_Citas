package com.hospital.citas.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hospital.citas.model.dto.PaginacionDTO;
import com.hospital.citas.model.dto.PanelGestionPacienteDTO;
import com.hospital.citas.repository.UsuarioRepository;

// Servicio dedicado a la consulta de los usuarios pacientes del sistema.
@Service
public class PanelGestionPacienteService {
    private final UsuarioRepository usuarioRepository;

    PanelGestionPacienteService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    // Consulta todos los usuarios pacientes del sistema.
    // Utilizado en el panel de gestión de pacientes del admin al cargar por primera vez.
    public List<PanelGestionPacienteDTO> listaUsuariosPacienteDTO (int numPagina) {
        int cantidadElementosPorPagina = 5;
        return usuarioRepository.listaUsuarPacienteDTOs(numPagina, cantidadElementosPorPagina);
    }

    // Consulta todos los usuarios pacientes del sistema.
    // Utilizado en el panel de gestión de pacientes del admin, para la paginación de elementos.
    public List<PanelGestionPacienteDTO> listaUsuariosPacienteDTOPagination (PaginacionDTO paginacionAttr) {
        int cantidadElementosPorPagina = 5;
        return usuarioRepository.listaUsuarPacienteDTOs(paginacionAttr.getNumeroPagina(), cantidadElementosPorPagina);
    }

    // Consulta la cantidad de usuarios paciente que hay en el sistema.
    // Utilizado en la paginación del panel de gestión de usuarios.
    public int consultaCantidadUsuariosPaciente() {
        return usuarioRepository.consultaCantidadUsuariosPaciente();
    }
}
