package com.hospital.citas.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Entity
@Table (name = "TipoIdentificaciones")
public class TipoIdentificacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull(message = "Debe seleccionar el tipo de identificación.")
    @Positive(message = "El tipo de identificación debe ser numérico positivo.")
    private Long id;

    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    public TipoIdentificacion(Long id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }

    public TipoIdentificacion() {
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
}
