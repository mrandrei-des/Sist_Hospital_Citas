package com.hospital.citas.model.dto;

// DTO para enviar los parámetros de paginación desde JavaScript hacia el API
public class PaginacionDTO {
    private Integer numeroPagina;
    private Integer cantidadRegistrosPorPagina;
    public PaginacionDTO() {
    }
    public PaginacionDTO(Integer numeroPagina, Integer cantidadRegistrosPorPagina) {
        this.numeroPagina = numeroPagina;
        this.cantidadRegistrosPorPagina = cantidadRegistrosPorPagina;
    }
    public Integer getNumeroPagina() {
        return numeroPagina;
    }
    public Integer getCantidadRegistrosPorPagina() {
        return cantidadRegistrosPorPagina;
    }
    
}
