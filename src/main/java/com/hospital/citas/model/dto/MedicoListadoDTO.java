package com.hospital.citas.model.dto;

// DTO para la consulta de médicos para el listado en el mantenimiento de médicos.
public class MedicoListadoDTO {
    private Long id;
    private String nombre;
    private String primerApellido;
    private String segundoApellido;
    private String nombreEspecialidad;
    private boolean eliminable; 

    public MedicoListadoDTO() {
    }

    public MedicoListadoDTO(Long id, String nombre, String primerApellido, String segundoApellido, String nombreEspecialidad, boolean eliminable) {
        this.id = id;
        this.nombre = nombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.nombreEspecialidad = nombreEspecialidad;
        this.eliminable = eliminable;
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

    public void setNombreEspecialidad(String nombreEspecialidad) {
        this.nombreEspecialidad = nombreEspecialidad;
    }

    public boolean isEliminable() {
        return eliminable;
    }

    public void setEliminable(boolean eliminable) {
        this.eliminable = eliminable;
    }
}
