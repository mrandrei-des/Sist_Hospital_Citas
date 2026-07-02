package com.hospital.citas.model.entity;

import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "CodigosRecuperacionContrasenna_Activos", uniqueConstraints = @UniqueConstraint(
    name = "UK_codigoGenerado", columnNames = {"codigoGenerado"}
))
public class CodigoResetContrasenna {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id;

    @Column(name = "codigoGenerado", nullable = false)
    @NotBlank(message = "Debe indicar el código recibido.")
    private String codigoGenerado;
    
    @OneToOne
    @JoinColumn (name = "idUsuario", nullable = false)
    private Usuario usuario;
    
    @Column(name = "fechaHoraExpiracion", nullable = false)
    private LocalDateTime fechaExpiracion;

    public CodigoResetContrasenna() {
    }

    public CodigoResetContrasenna(Long id, String codigoGenerado, Usuario usuario, LocalDateTime fechaExpiracion) {
        this.id = id;
        this.codigoGenerado = codigoGenerado;
        this.usuario = usuario;
        this.fechaExpiracion = fechaExpiracion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigoGenerado() {
        return codigoGenerado;
    }

    public void setCodigoGenerado(String codigoGenerado) {
        this.codigoGenerado = codigoGenerado;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public LocalDateTime getFechaExpiracion() {
        return fechaExpiracion;
    }

    public void setFechaExpiracion(LocalDateTime fechaExpiracion) {
        this.fechaExpiracion = fechaExpiracion;
    }

    public boolean estaExpirado() {
        return LocalDateTime.now().isAfter(fechaExpiracion);
    }
}
