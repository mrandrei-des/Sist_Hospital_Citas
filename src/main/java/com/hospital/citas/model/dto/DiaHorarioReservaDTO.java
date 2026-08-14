package com.hospital.citas.model.dto;

import java.time.LocalDate;
import java.util.List;

// DTO para la construcción del horario médico por cada día donde cada espacio de atención se guarda de forma compleja manejando estados para así renderizarlo.
public class DiaHorarioReservaDTO {
    private Long idDiaSemana;
    private String nombreDia;
    private String fechaFormateada;
    private LocalDate fecha;
    private List<EspacioHorarioDTO> listaEspacios;
    public DiaHorarioReservaDTO() {
    }
    public DiaHorarioReservaDTO(Long idDiaSemana, String nombreDia, String fechaFormateada, LocalDate fecha,
            List<EspacioHorarioDTO> listaEspacios) {
        this.idDiaSemana = idDiaSemana;
        this.nombreDia = nombreDia;
        this.fechaFormateada = fechaFormateada;
        this.fecha = fecha;
        this.listaEspacios = listaEspacios;
    }
    public Long getIdDiaSemana() {
        return idDiaSemana;
    }
    public void setIdDiaSemana(Long idDiaSemana) {
        this.idDiaSemana = idDiaSemana;
    }
    public String getNombreDia() {
        return nombreDia;
    }
    public void setNombreDia(String nombreDia) {
        this.nombreDia = nombreDia;
    }
    public String getFechaFormateada() {
        return fechaFormateada;
    }
    public void setFechaFormateada(String fechaFormateada) {
        this.fechaFormateada = fechaFormateada;
    }
    public LocalDate getFecha() {
        return fecha;
    }
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
    public List<EspacioHorarioDTO> getListaEspacios() {
        return listaEspacios;
    }
    public void setListaEspacios(List<EspacioHorarioDTO> listaEspacios) {
        this.listaEspacios = listaEspacios;
    }
}
