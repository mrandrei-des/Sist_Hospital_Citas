package com.hospital.citas.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.hospital.citas.model.dto.RolFormularioDTO;
import com.hospital.citas.model.entity.Rol;
import com.hospital.citas.repository.RolRepository;

// Servicio para la consulta de los roles que pueden tener los usuarios en el sistema.
@Service
public class RolService {
    private final RolRepository rolRepository;

    RolService(RolRepository rolRepository) {
        this.rolRepository = rolRepository;
    }

    // Consulta los roles que pueden tener los usuarios en el sistema.
    // Usado para cargar el select de roles en el mantenimiento y creación de usuarios del admin
    public List<RolFormularioDTO> consultarRolesDTO(){
        return buscarRolesFormulario();
    }

    // Consulta, construye la lista y devuelve los roles de que pueden tener los usuarios en el sistema.
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
