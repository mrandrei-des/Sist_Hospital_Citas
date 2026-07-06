package com.hospital.citas.model.dto;

public class MedicoRegistradoDTO {
    private Long id;
    private String nombre;
    private String primerApellido;
    private String segundoApellido;
    private String nombreEspecialidad;

    public MedicoRegistradoDTO() {
    }

    public MedicoRegistradoDTO(Long id, String nombre, String primerApellido, String segundoApellido, String nombreEspecialidad) {
        this.id = id;
        this.nombre = nombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.nombreEspecialidad = nombreEspecialidad;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }
    
    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public String getNombreEspecialidad() {
        return nombreEspecialidad;
    }

    public void setIdEspecialidad(String nombreEspecialidad) {
        this.nombreEspecialidad = nombreEspecialidad;
    }
}
