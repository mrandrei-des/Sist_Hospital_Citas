package com.hospital.citas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hospital.citas.model.dto.CitaPacientesDTO;
import com.hospital.citas.model.dto.HistorialMedicoPacienteDTO;
import com.hospital.citas.model.dto.ReporteCitaCsvDTO;
import com.hospital.citas.model.entity.Estado;
import com.hospital.citas.model.entity.Medico;
import com.hospital.citas.model.entity.ReservaCitas;

import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

// Repository para el manejo y consulta de insumos necesarios para la reserva de citas médicas.
public interface ReservaCitasRepository extends JpaRepository<ReservaCitas, Long> {
    List<ReservaCitas> findAllByMedicoAndFechaAndHoraAndEstadoIn(Medico medico, LocalDate fecha, LocalTime hora, List<Estado> estado);
    List<ReservaCitas> findAllByMedico(Medico medico);

    @Transactional
    @Modifying
    @Query(value = "{call sp_Inserta_Registro_Bitacora_ReservaCitas(:idAccion, :idReservaAfectada, :descripcionAccion, :idUsuarioRealizoAccion)}", nativeQuery = true)
    void insertaRegistroBitacoraCambiosReservaCita(
        @Param("idAccion") Long idAccion,
        @Param("idReservaAfectada") Long idReservaAfectada,
        @Param("descripcionAccion") String descripcionAccion,
        @Param("idUsuarioRealizoAccion") Long idUsuarioRealizoAccion
    );

    @Query(value = "{call sp_ConsultaHorasOcupadasUsuarioPorFecha(:idUsuario, :fechaBusqueda)}", nativeQuery = true)
    Optional<List<LocalTime>> consultaHorasRestringidasUsuarioPorFecha(
        @Param("idUsuario") Long idUsuario,
        @Param("fechaBusqueda") LocalDate fechaBusqueda
    );

    @Query(value = "{call sp_ConsultaHorasOcupadasMedicoPorFecha(:idMedico, :fechaBusqueda)}", nativeQuery = true)
    Optional<List<LocalTime>> consultaHorasOcupadasMedicoPorFecha(
        @Param("idMedico") Long idMedico,
        @Param("fechaBusqueda") LocalDate fechaBusqueda
    );
    
    @Query(value = "{call sp_consultaHistorialMedicoPaciente(:idUsuario, :numeroPagina, :tamannoPagina)}", nativeQuery = true)
    List<HistorialMedicoPacienteDTO> consultaHistorialMedicoPaciente(
        @Param("idUsuario") Long idUsuario,
        @Param("numeroPagina") Integer numeroPagina,
        @Param("tamannoPagina") Integer cantidadElementosPorPagina
    );

    @Query(value = "{call sp_consultaCantidadCitasReservadasPorPaciente(:idUsuario)}", nativeQuery = true)
    int consultaCantidadCitasReservadasPorPaciente(
        @Param("idUsuario") Long idUsuario
    );

    @Query(value = "{call sp_consultaHistorialMedicoPendientePaciente(:idUsuario)}", nativeQuery = true)
    List<HistorialMedicoPacienteDTO> consultaHistorialMedicoPendientePaciente(@Param("idUsuario") Long idUsuario);

    @Query(value = "{call sp_consultaCitasPacientes(:numeroPagina, :tamannoPagina)}", nativeQuery = true)
    List<CitaPacientesDTO> consultaCitasPacientes(
        @Param("numeroPagina") int numeroPagina,
        @Param("tamannoPagina") int tamannoPagina
    );

    @Query(value = "{call sp_consultaCitasPacientesFiltros(:filtEstado, :filtEspecialidad, :filtMedico, :filtFechaInicio, :filtFechaFin, :numeroPagina, :tamannoPagina)}", nativeQuery = true)
    List<CitaPacientesDTO> consultaCitasPacientesConFiltros(
        @Param("filtEstado") Long filtEstado,
        @Param("filtEspecialidad") Long filtEspecialidad,
        @Param("filtMedico") Long filtMedico,
        @Param("filtFechaInicio") LocalDate filtFechaInicio,
        @Param("filtFechaFin") LocalDate filtFechaFin,
        @Param("numeroPagina") int numeroPagina,
        @Param("tamannoPagina") int tamannoPagina
    );

    @Query(value = "{call sp_consultaCitasPendientesParaBot(:fechaCorte, :horaCorte)}", nativeQuery = true)
    Optional<List<ReservaCitas>> consultaCitasPendientesParaBot(
        @Param("fechaCorte") LocalDate fechaCorte,
        @Param("horaCorte") LocalTime horaCorte
    );
    
    @Query(value = "{call sp_consultaCantidadCitasPacientesFiltros(:filtEstado, :filtEspecialidad, :filtMedico, :filtFechaInicio, :filtFechaFin)}", nativeQuery = true)
    int consultaCantidadCitasPacientesConFiltros(
        @Param("filtEstado") Long filtEstado,
        @Param("filtEspecialidad") Long filtEspecialidad,
        @Param("filtMedico") Long filtMedico,
        @Param("filtFechaInicio") LocalDate filtFechaInicio,
        @Param("filtFechaFin") LocalDate filtFechaFin
    );

    @Query(value = "{call sp_consultaCitasPacientesFiltrosParaReporte(:filtEstado, :filtEspecialidad, :filtMedico, :filtFechaInicio, :filtFechaFin)}", nativeQuery = true)
    List<ReporteCitaCsvDTO> consultaCitasReporteCsv(
        @Param("filtEstado") Long filtEstado,
        @Param("filtEspecialidad") Long filtEspecialidad,
        @Param("filtMedico") Long filtMedico,
        @Param("filtFechaInicio") LocalDate filtFechaInicio,
        @Param("filtFechaFin") LocalDate filtFechaFin
    );


}