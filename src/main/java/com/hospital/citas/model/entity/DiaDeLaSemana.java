package com.hospital.citas.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table (name = "DiasDeLaSemana")
public class DiaDeLaSemana {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id;

    @Column(name = "inicial", nullable = false)
    private String inicial;

    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    public DiaDeLaSemana() {
    }

    public DiaDeLaSemana(Long id, String inicial, String descripcion) {
        this.id = id;
        this.inicial = inicial;
        this.descripcion = descripcion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInicial() {
        return inicial;
    }

    public void setInicial(String inicial) {
        this.inicial = inicial;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }    
}
