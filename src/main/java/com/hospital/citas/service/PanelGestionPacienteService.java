package com.hospital.citas.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hospital.citas.model.dto.PanelGestionPacienteDTO;
import com.hospital.citas.repository.UsuarioRepository;

@Service
public class PanelGestionPacienteService {
    private final UsuarioRepository usuarioRepository;

    PanelGestionPacienteService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<PanelGestionPacienteDTO> listaUsuariosPacienteDTO () {
        return usuarioRepository.listaUsuarPacienteDTOs();
    }
}
