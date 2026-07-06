package com.hospital.citas.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hospital.citas.model.dto.EspecialidadDTO;
import com.hospital.citas.model.dto.MedicoDTO;
import com.hospital.citas.model.dto.MedicoRegistradoDTO;
import com.hospital.citas.model.entity.Especialidad;
import com.hospital.citas.model.entity.Estado;
import com.hospital.citas.model.entity.Medico;
import com.hospital.citas.repository.MedicoRepository;

@Service
public class MedicoService {
    private final MedicoRepository medicoRepository;
    private EspecialidadService especialidadService;

    public MedicoService(MedicoRepository medicoRepository, EspecialidadService especialidadService) {
        this.medicoRepository = medicoRepository;
        this.especialidadService = especialidadService;
    }

    public List<Medico> listarMedicosPorEstado(Long estadoMedicos){
        Estado estado = new Estado();
        estado.setId(estadoMedicos);
        return medicoRepository.findAllByEstado(estado);
    }

    public List<EspecialidadDTO> listaEspecialidadesDTO() {
        return especialidadService.listarEspecialidades(4L);
    }

    public boolean procesarMedico(MedicoDTO medicoDTO) {
        Medico medico = new Medico();
        Especialidad especialidad = new Especialidad();
        Estado estado = new Estado();
        String mensajeBitacora = "El médico ha sido registrado en el sistema.";
        Long idAccion = 1L;

        if(medicoDTO.getId() != null) {
            medico.setId(medicoDTO.getId());
            mensajeBitacora = "El médico ha sido actualizado.";
            idAccion = 2L;
        }

        especialidad.setId(medicoDTO.getIdEspecialidad());
        estado.setId(4L);

        medico.setNombre(medicoDTO.getNombre());
        medico.setPrimerApellido(medicoDTO.getPrimerApellido());
        medico.setSegundoApellido(medicoDTO.getSegundoApellido());
        medico.setEspecialidad(especialidad);
        medico.setEstado(estado);

        Medico medicoRegistrado = medicoRepository.save(medico);
        if (medicoRegistrado != null) {
            medicoRepository.insertaRegistroBitacoraCambios(idAccion, medicoRegistrado.getId(), mensajeBitacora, 1L);
            return true;
        }
        return false;
    }

    public List<MedicoRegistradoDTO> listaMedicoRegistradoDTOs() {
        return medicoRepository.listaMedicosRegistradoMedicoRegistradoDTOs();
    }

    public MedicoDTO buscarPorId(Long id) {
        Medico medico = medicoRepository.findById(id).orElse(null);
        MedicoDTO dto = new MedicoDTO();

        if(medico != null) {
            dto.setId(medico.getId());
            dto.setNombre(medico.getNombre());
            dto.setPrimerApellido(medico.getPrimerApellido());
            dto.setSegundoApellido(medico.getSegundoApellido());
            dto.setIdEspecialidad(medico.getEspecialidad().getId());
        }
        return dto;
    }

    public void eliminarPorId(Long id) {
        medicoRepository.eliminarMedico(id);
        medicoRepository.insertaRegistroBitacoraCambios(3L, id, "El médico ha sido eliminado.", 1L);
    }
}
