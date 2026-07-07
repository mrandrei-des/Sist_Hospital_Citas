package com.hospital.citas.model.dto;

import java.time.LocalDate;
import java.util.List;

public class DiaHorarioDTO {
    private Long idDiaSemana;
    private String nombreDia;
    private String fechaFormateada;
    private LocalDate fecha;
    private List<String> listaEspacios;
    public DiaHorarioDTO() {
    }
    public DiaHorarioDTO(Long idDiaSemana, String nombreDia, String fechaFormateada, LocalDate fecha,
            List<String> listaEspacios) {
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
    public List<String> getListaEspacios() {
        return listaEspacios;
    }
    public void setListaEspacios(List<String> listaEspacios) {
        this.listaEspacios = listaEspacios;
    }
}
