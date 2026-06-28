package com.hospital.citas.model.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table (name = "PermisosPorRol")
public class PermisoPorRol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id;

    @ManyToOne
    @JoinColumn(name = "idRol", nullable = false)
    private Rol rol;

    @ManyToOne
    @JoinColumn(name = "idPermiso", nullable = false)    
    private Permiso permiso;

    @ManyToOne
    @JoinColumn(name = "idUsuarioRegistro", nullable = false)    
    private Usuario usuario;

    @Column(name = "fechaHoraRegistro", nullable = false)
    private LocalDateTime fechaHoraRegistro;

    public PermisoPorRol() {
    }

    public PermisoPorRol(Long id, Rol rol, Permiso permiso, Usuario usuario, LocalDateTime fechaHoraRegistro) {
        this.id = id;
        this.rol = rol;
        this.permiso = permiso;
        this.usuario = usuario;
        this.fechaHoraRegistro = fechaHoraRegistro;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public Permiso getPermiso() {
        return permiso;
    }

    public void setPermiso(Permiso permiso) {
        this.permiso = permiso;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public LocalDateTime getFechaHoraRegistro() {
        return fechaHoraRegistro;
    }

    public void setFechaHoraRegistro(LocalDateTime fechaHoraRegistro) {
        this.fechaHoraRegistro = fechaHoraRegistro;
    }
}
