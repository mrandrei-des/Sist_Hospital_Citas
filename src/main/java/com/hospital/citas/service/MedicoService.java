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

    // Método que busca a todos los médicos que tengan el estado indicado.
    // Utilizado al renderizar los card de médico en el mantenimiento de médicos.
    public List<Medico> listarMedicosPorEstado(Long estadoMedicos){
        Estado estado = new Estado();
        estado.setId(estadoMedicos);
        return medicoRepository.findAllByEstado(estado);
    }

    // Método que busca al médico por id y por el estado indicado.
    // Utilizado en las validaciones de reserva de citas médicas.
    public Medico buscarPorIdYEstado(Long idMedico, Long idEstado) {
        Estado estado = new Estado();
        estado.setId(idEstado);
        
        Medico medico = medicoRepository.findByIdAndEstado(idMedico, estado).orElse(null);
        return medico;
    }

    // Consulta las especialidades médicas activas que se encuentran registradas en el sistema.
    // Utilizado para cargar las opciones de select de filtro de especialidad.
    public List<EspecialidadDTO> listaEspecialidadesDTO() {
        return especialidadService.listarEspecialidades(4L);
    }

    // Método que registra o actualiza un médico.
    // Utilizado en la creación y mantenimiento de médicos.
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

    // Consulta a todos los médicos que se encuentren registrados en el sistema.
    // Utilizado al cargar la lista de médicos registrados en el mantenimiento de médicos.
    public List<MedicoRegistradoDTO> listaMedicoRegistradoDTOs() {
        return medicoRepository.listaMedicosRegistradoMedicoRegistradoDTOs();
    }

    // Consulta y devuelve la lista de médicos registrados en el sistema.
    // Utilizado para cargar los card de médicos a editar o eliminar en el mantenimiento de médicos.
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

    // Busca y devuelve al médico que encuentre con el id indicado.
    // Utilizado al editar la información del médico.
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

    // Método que elimina al médico indicado en el id que se le pasa.
    // Usado al eliminar a los médicos que no tienen citas reservadas.
    public boolean eliminarPorId(Long id, Long idUsuarioLoggeado) {
        try {
            medicoRepository.eliminarMedico(id);
            medicoRepository.insertaRegistroBitacoraCambios(3L, id, "El médico ha sido eliminado.", idUsuarioLoggeado);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Consulta el nombre completo del médico que corresponda al id indicado.
    // Usado para cargar nombre completo del médico en el registro de horarios médicos.
    public String consultaNombreMedicoPorId(Long idMedico) {
        MedicoDTO dto = buscarPorId(idMedico);
        return dto.getNombre() + " " + dto.getPrimerApellido() + " " + dto.getSegundoApellido();
    }

    // Consulta todos los médicos que pertenecen a la especialidad indicada.
    // Usado para cargar los select de filtros de médicos.
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

    // Consulta los médicos que ya tienen un horario de atención definido.
    // Usado para cargar el select de médicos en la vista del horario médico. 
    public List<MedicoDTO> listaMedicosConHorario() {
        return medicoRepository.listaMedicosConHorarioCreado();
    }

    // Busca y devuelve a los médicos que tengan el dato indicado en alguna parte de su nombre.
    // Usado en el filtro de médicos de la reserva de citas médicas.
    public List<MedicoReservaDTO> listaMedicosReservaPorEspecialidad(Long idEspecialidad, String filtroBusqueda) {
        String queryBusqueda = "%" + filtroBusqueda + "%";
        List<MedicoReservaDTO> listaRegistros = medicoRepository.consultarMedicosPorEspecialidadReserva(idEspecialidad, queryBusqueda);
        return listaRegistros != null ? listaRegistros : new ArrayList<MedicoReservaDTO>();
    }

    // Consulta a todos los médicos que pertenecen a la especialidad indicada.
    // Usado para cargar los option del select de filtros.
    public List<MedicoReservaDTO> listaMedicosFiltrosPorEspecialidad(Long idEspecialidad) {
        List<Medico> listaMedicos = new ArrayList<>();
        List<MedicoReservaDTO> listaMedicosDTO = new ArrayList<>();
        MedicoReservaDTO dto;
        Estado estado = new Estado();
        estado.setId(4L);

        if(idEspecialidad.equals(0L)) {
            listaMedicos = medicoRepository.findAllByEstado(estado);
        }else {
            listaMedicos = medicoRepository.findAllByEstadoAndEspecialidadId(estado, idEspecialidad);
        }

        if(listaMedicos.size() > 0) {
            for (Medico medico : listaMedicos) {
                dto = new MedicoReservaDTO();
                dto.setId(medico.getId());
                dto.setNombre(medico.getNombre());
                dto.setPrimerApellido(medico.getPrimerApellido());
                dto.setSegundoApellido(medico.getSegundoApellido());
                listaMedicosDTO.add(dto);
            }
        }

        return listaMedicosDTO;
    }

    // Consulta todos los médicos que tengan citas médicas pendientes, confirmadas o canceldas.
    // Usado en la carga de los select de filtros de reporte de citas.
    public List<MedicoReservaDTO> listaMedicosConCitas(){
        return medicoRepository.listaMedicosConCitas();
    }

    // Consulta todos los médicos que tengan citas médicas pendientes, confirmadas o canceldas y además, que pertecen a la especialidad indicada.
    // Usado para cargar el select de médicos en la reserva de citas.
    public List<MedicoReservaDTO> listaMedicosConCitasPorEspecialidad(Long idEspecialidad){
        return medicoRepository.listaMedicosConCitasPorEspecialidad(idEspecialidad);
    }
}