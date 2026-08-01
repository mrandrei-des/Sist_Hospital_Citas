package com.hospital.citas.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.hospital.citas.model.dto.ReservaCitasReservaDTO;
import com.hospital.citas.model.entity.Estado;
import com.hospital.citas.model.entity.Medico;
import com.hospital.citas.model.entity.ReservaCitas;
import com.hospital.citas.repository.ReservaCitasRepository;

@Service
public class ReservaCitasService {
    private final ReservaCitasRepository reservaCitasRepository;

    ReservaCitasService(ReservaCitasRepository reservaCitasRepository) {
        this.reservaCitasRepository = reservaCitasRepository;
    }

    public List<ReservaCitasReservaDTO> buscarCitasReservadasPorMedicoFechaHoraEstados(Long idMedico, LocalDate fecha, LocalTime hora, List<Long> listaEstados) {
        List<ReservaCitasReservaDTO> listaReservas = new ArrayList<>();

        Medico medico = new Medico();
        medico.setId(idMedico);

        List<Estado> listaObjEstados = new ArrayList<>();
        Estado objEstado;
        for (Long estado : listaEstados) {
            objEstado = new Estado();
            objEstado.setId(estado);
            listaObjEstados.add(objEstado);
        }

        List<ReservaCitas> listaReservasEncontradas = reservaCitasRepository.findAllByMedicoAndFechaAndHoraAndEstadoIn(medico, fecha, hora, listaObjEstados);

        if(listaReservasEncontradas.size() > 0) {
            ReservaCitasReservaDTO objReserva;
            for (ReservaCitas reserva : listaReservasEncontradas) {
                objReserva = new ReservaCitasReservaDTO();
                objReserva.setIdMedico(reserva.getMedico().getId());
                objReserva.setIdUsuario(reserva.getUsuario().getId());
                objReserva.setFecha(reserva.getFecha());
                objReserva.setHora(reserva.getHora());
                listaReservas.add(objReserva);
            }
        }
        return listaReservas;
    }
    
}
