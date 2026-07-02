package com.hospital.citas.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.hospital.citas.model.dto.RolFormularioDTO;
import com.hospital.citas.model.entity.Rol;
import com.hospital.citas.repository.RolRepository;

@Service
public class RolService {
    private final RolRepository rolRepository;

    RolService(RolRepository rolRepository) {
        this.rolRepository = rolRepository;
    }

    public List<RolFormularioDTO> consultarRolesDTO(){
        return buscarRolesFormulario();
    }

    private List<RolFormularioDTO> buscarRolesFormulario() {
        List<Rol> listaRoles = rolRepository.findAll();
        List<RolFormularioDTO> listaRolesDTO = new ArrayList<>();
        RolFormularioDTO dto;
        for (Rol rol : listaRoles) {
            dto = new RolFormularioDTO();
            dto.setId(rol.getId());
            dto.setDescripcion(rol.getDescripcion());
            listaRolesDTO.add(dto);
        }

        return listaRolesDTO;
    }
}
