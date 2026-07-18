package com.hospital.citas.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.hospital.citas.model.dto.EspecialidadDTO;
import com.hospital.citas.model.dto.UltimaEspecialidadRegistradaDTO;
import com.hospital.citas.model.entity.Especialidad;
import com.hospital.citas.model.entity.Estado;
import com.hospital.citas.repository.EspecialidadRepository;

@Service
public class EspecialidadService {
    private final EspecialidadRepository especialidadRepository;

    EspecialidadService(EspecialidadRepository especialidadRepository) {
        this.especialidadRepository = especialidadRepository;
    }

    public boolean registrarEspecialidad(EspecialidadDTO especialidadDTO, Long idUsuarioLoggeado) {
        Especialidad especialidad = new Especialidad();
        Estado estado = new Estado();
        String mensajeBitacora = "La especialidad ha sido registrada en el sistema.";
        Long idAccion = 1L;

        if(especialidadDTO.getId() != null) {
            especialidad.setId(especialidadDTO.getId());
            mensajeBitacora = "A la especialidad se le modificó la descripción.";
            idAccion = 2L;
        }

        estado.setId(4L);
        especialidad.setDescripcion(especialidadDTO.getDescripcion());
        especialidad.setEstado(estado);

        Especialidad especialidadRegistrada = especialidadRepository.save(especialidad);
        if (especialidadRegistrada != null) {
            especialidadRepository.insertaRegistroBitacoraCambios(idAccion, especialidadRegistrada.getId(), mensajeBitacora, idUsuarioLoggeado);
            return true;
        }
        return false;
    }

    public List<EspecialidadDTO> listarEspecialidades(Long estadoEspecialidades) {
        Estado estado = new Estado();
        estado.setId(estadoEspecialidades);

        List<Especialidad> listaEspecialidades = especialidadRepository.findAllByEstado(estado);
        List<EspecialidadDTO> listaDTO = new ArrayList<>();
        EspecialidadDTO dto;

        for (Especialidad especialidad : listaEspecialidades) {
            dto = new EspecialidadDTO();
            dto.setId(especialidad.getId());
            dto.setDescripcion(especialidad.getDescripcion());
            listaDTO.add(dto);
        }
        return listaDTO;
    }

    public List<EspecialidadDTO> listarEspecialidadesConMedicos(Long estadoEspecialidades) {
        List<Especialidad> listaEspecialidades = especialidadRepository.consultarEspecialidadesConMedico(estadoEspecialidades);
        List<EspecialidadDTO> listaDTO = new ArrayList<>();
        EspecialidadDTO dto;

        for (Especialidad especialidad : listaEspecialidades) {
            dto = new EspecialidadDTO();
            dto.setId(especialidad.getId());
            dto.setDescripcion(especialidad.getDescripcion());
            listaDTO.add(dto);
        }
        return listaDTO;
    }

    public EspecialidadDTO buscarPorId(Long id) {
        Especialidad especialidad = especialidadRepository.findById(id).orElse(null);
        EspecialidadDTO dto = new EspecialidadDTO();

        if(especialidad != null) {
            dto.setId(especialidad.getId());
            dto.setDescripcion(especialidad.getDescripcion());
        }
        return dto;
    }

    public List<UltimaEspecialidadRegistradaDTO> listarUltimaEspecialidadRegistradaDTOs (){
        return especialidadRepository.listaUltimasEspecialidadRegistradasDtos();
    }
}
