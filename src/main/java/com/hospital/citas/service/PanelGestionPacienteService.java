package com.hospital.citas.service;

import java.util.List;

import org.springframework.stereotype.Service;

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
    // Utilizado en el panel de gestión de pacientes del admin.
    public List<PanelGestionPacienteDTO> listaUsuariosPacienteDTO () {
        return usuarioRepository.listaUsuarPacienteDTOs();
    }
}
