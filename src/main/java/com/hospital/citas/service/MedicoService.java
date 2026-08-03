package com.hospital.citas.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import com.hospital.citas.model.dto.EspecialidadDTO;
import com.hospital.citas.model.dto.MedicoDTO;
import com.hospital.citas.model.dto.MedicoListadoDTO;
import com.hospital.citas.model.dto.MedicoRegistradoDTO;
import com.hospital.citas.model.dto.MedicoReservaDTO;
import com.hospital.citas.model.entity.Especialidad;
import com.hospital.citas.model.entity.Estado;
import com.hospital.citas.model.entity.Medico;
import com.hospital.citas.model.entity.ReservaCitas;
import com.hospital.citas.repository.MedicoRepository;

@Service
public class MedicoService {
    private final MedicoRepository medicoRepository;
    private EspecialidadService especialidadService;
    private final ReservaCitasService reservaCitasService;

    public MedicoService(MedicoRepository medicoRepository, EspecialidadService especialidadService, ReservaCitasService reservaCitasService) {
        this.medicoRepository = medicoRepository;
        this.especialidadService = especialidadService;
        this.reservaCitasService = reservaCitasService;
    }

    public List<Medico> listarMedicosPorEstado(Long estadoMedicos){
        Estado estado = new Estado();
        estado.setId(estadoMedicos);
        return medicoRepository.findAllByEstado(estado);
    }

    public Medico buscarPorIdYEstado(Long idMedico, Long idEstado) {
        Estado estado = new Estado();
        estado.setId(idEstado);
        
        Medico medico = medicoRepository.findByIdAndEstado(idMedico, estado).orElse(null);
        return medico;
    }

    public List<EspecialidadDTO> listaEspecialidadesDTO() {
        return especialidadService.listarEspecialidades(4L);
    }

    public boolean procesarMedico(MedicoDTO medicoDTO, Long idUsuarioLoggeado) {
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
            medicoRepository.insertaRegistroBitacoraCambios(idAccion, medicoRegistrado.getId(), mensajeBitacora, idUsuarioLoggeado);
            return true;
        }
        return false;
    }

    public List<MedicoRegistradoDTO> listaMedicoRegistradoDTOs() {
        return medicoRepository.listaMedicosRegistradoMedicoRegistradoDTOs();
    }

    public List<MedicoListadoDTO> listadoMedicoDTO() {
        List<MedicoListadoDTO> listadoMedicosDTO = new ArrayList<>();
        List<MedicoRegistradoDTO> listadoMedicos = medicoRepository.listaMedicosRegistradoMedicoRegistradoDTOs();
        MedicoListadoDTO dto;
        List<ReservaCitas> listaCitasMedico;

        for (MedicoRegistradoDTO medico : listadoMedicos) {
            listaCitasMedico = reservaCitasService.listaCitasEncontradasPorMedico(medico.getId());

            dto = new MedicoListadoDTO();
            dto.setId(medico.getId());
            dto.setNombre(medico.getNombre());
            dto.setPrimerApellido(medico.getPrimerApellido());
            dto.setSegundoApellido(medico.getSegundoApellido());
            dto.setNombreEspecialidad(medico.getNombreEspecialidad());
            dto.setEliminable(listaCitasMedico.size() == 0);
            
            listadoMedicosDTO.add(dto);
        }
        return listadoMedicosDTO;
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

    public boolean eliminarPorId(Long id, Long idUsuarioLoggeado) {
        try {
            medicoRepository.eliminarMedico(id);
            medicoRepository.insertaRegistroBitacoraCambios(3L, id, "El médico ha sido eliminado.", idUsuarioLoggeado);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String consultaNombreMedicoPorId(Long idMedico) {
        MedicoDTO dto = buscarPorId(idMedico);
        return dto.getNombre() + " " + dto.getPrimerApellido() + " " + dto.getSegundoApellido();
    }

    public List<MedicoDTO> listarMedicosPorEspecialidad(Long idEspecialidad) {
        List<Medico> medicos = medicoRepository.findAllByEspecialidadId(idEspecialidad);
        List<MedicoDTO> listaMedicosDtos = new ArrayList<>();
        MedicoDTO dto;

        for (Medico medico : medicos) {
            dto = new MedicoDTO();
            dto.setId(medico.getId());
            dto.setNombre(medico.getNombre());
            dto.setPrimerApellido(medico.getPrimerApellido());
            dto.setSegundoApellido(medico.getSegundoApellido());
            listaMedicosDtos.add(dto);
        }
        return listaMedicosDtos;
    }

    public List<MedicoDTO> listaMedicosConHorario() {
        return medicoRepository.listaMedicosConHorarioCreado();
    }

    public List<MedicoReservaDTO> listaMedicosReservaPorEspecialidad(Long idEspecialidad, String filtroBusqueda) {
        String queryBusqueda = "%" + filtroBusqueda + "%";
        List<MedicoReservaDTO> listaRegistros = medicoRepository.consultarMedicosPorEspecialidadReserva(idEspecialidad, queryBusqueda);
        return listaRegistros != null ? listaRegistros : new ArrayList<MedicoReservaDTO>();
    }
}