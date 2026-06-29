package com.hospital.citas.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table (name = "Roles")
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id;

    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "estado", nullable = false)
    private Estado estado;

    @Column(name = "paginaInicio", nullable = false)
    private String nombrePaginaInicio;

    public Rol(Long id, String descripcion, Estado estado, String nombrePaginaInicio) {
        this.id = id;
        this.descripcion = descripcion;
        this.estado = estado;
        this.nombrePaginaInicio = nombrePaginaInicio;
    }

    public Rol() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public String getNombrePaginaInicio() {
        return nombrePaginaInicio;
    }

    public void setNombrePaginaInicio(String nombrePaginaInicio) {
        this.nombrePaginaInicio = nombrePaginaInicio;
    }
}
