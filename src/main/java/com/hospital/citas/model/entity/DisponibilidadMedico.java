package com.hospital.citas.model.entity;

import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table (name = "DisponibilidadMedicos")
public class DisponibilidadMedico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id;

    @ManyToOne
    @JoinColumn(name = "idMedico", nullable = false)
    private Medico medico;

    @ManyToOne
    @JoinColumn(name = "idDiaSemana", nullable = false)
    private DiaDeLaSemana diaDeLaSemana;

    @Column(name = "horaInicioAtencion", nullable = false)
    private LocalTime horaInicioAtencion;

    @Column(name = "horaFinAtencion", nullable = false)
    private LocalTime horaFinAtencion;

    @ManyToOne
    @JoinColumn(name = "estado", nullable = false)
    private Estado estado;

    public DisponibilidadMedico() {
    }

    public DisponibilidadMedico(Long id, Medico medico, DiaDeLaSemana diaDeLaSemana, LocalTime horaInicioAtencion,
            LocalTime horaFinAtencion, Estado estado) {
        this.id = id;
        this.medico = medico;
        this.diaDeLaSemana = diaDeLaSemana;
        this.horaInicioAtencion = horaInicioAtencion;
        this.horaFinAtencion = horaFinAtencion;
        this.estado = estado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public DiaDeLaSemana getDiaDeLaSemana() {
        return diaDeLaSemana;
    }

    public void setDiaDeLaSemana(DiaDeLaSemana diaDeLaSemana) {
        this.diaDeLaSemana = diaDeLaSemana;
    }

    public LocalTime getHoraInicioAtencion() {
        return horaInicioAtencion;
    }

    public void setHoraInicioAtencion(LocalTime horaInicioAtencion) {
        this.horaInicioAtencion = horaInicioAtencion;
    }

    public LocalTime getHoraFinAtencion() {
        return horaFinAtencion;
    }

    public void setHoraFinAtencion(LocalTime horaFinAtencion) {
        this.horaFinAtencion = horaFinAtencion;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }
}
