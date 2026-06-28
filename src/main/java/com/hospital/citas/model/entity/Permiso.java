package com.hospital.citas.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table (name = "Permisos", uniqueConstraints = @UniqueConstraint(
    name = "UK_titulo_permiso", columnNames = {"titulo"}
))
public class Permiso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id;

    @Column(name = "titulo", nullable = false)
    private String titulo;

    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "estado", nullable = false)
    private Estado estado;

    @ManyToOne
    @JoinColumn(name = "idGrupoPermiso", nullable = false)
    private GrupoPermiso grupoPermiso;

    public Permiso() {
    }

    public Permiso(Long id, String titulo, String descripcion, Estado estado, GrupoPermiso grupoPermiso) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.estado = estado;
        this.grupoPermiso = grupoPermiso;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
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

    public GrupoPermiso getGrupoPermiso() {
        return grupoPermiso;
    }

    public void setGrupoPermiso(GrupoPermiso grupoPermiso) {
        this.grupoPermiso = grupoPermiso;
    }    
}
