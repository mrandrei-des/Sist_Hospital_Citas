package com.hospital.citas.model.dto;

// DTO con la estructura para la consulta de médicos en la reserva de citas médicas.
public class MedicoReservaDTO {
    private Long id;
    private String nombre;
    private String primerApellido;
    private String segundoApellido;

    public MedicoReservaDTO() {
    }

    public MedicoReservaDTO(Long id, String nombre, String primerApellido, String segundoApellido) {
        this.id = id;
        this.nombre = nombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
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
}
