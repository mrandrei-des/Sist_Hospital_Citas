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
import jakarta.validation.constraints.NotBlank;

@Entity
@Table (name = "Usuarios", uniqueConstraints = @UniqueConstraint(
    name = "UK_cedula_correo_usuario", columnNames = {"identificacion", "correoElectronico"}
))
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "idTipoIdentificacion", nullable = false)
    private TipoIdentificacion tipoIdentificacion;

    @Column(name = "identificacion", nullable = false)
    @NotBlank(message = "Debe indicar el número de identificación.")
    private String identificacion;

    @Column(name = "ContrasennaHash", nullable = false)
    @NotBlank(message = "Debe indicar una contraseña.")
    private String contrasennaHash;
    
    @Column(name = "nombre", nullable = false)
    @NotBlank(message = "Debe indicar el nombre.")
    private String nombre;
    
    @Column(name = "primerApellido", nullable = false)
    @NotBlank(message = "Debe indicar el primer apellido.")
    private String primerApellido;
    
    @Column(name = "segundoApellido", nullable = false)
    @NotBlank(message = "Debe indicar el segundo apellido.")
    private String segundoApellido;
    
    @Column(name = "correoElectronico", nullable = false)
    @NotBlank(message = "Debe indicar el correo electrónico.")
    private String correoElectronico;

    @ManyToOne
    @JoinColumn (name = "estado", nullable = false)
    private Estado estado;

    @ManyToOne
    @JoinColumn (name = "idRol", nullable = false)
    private Rol rol;

    public Usuario() {        
    }

    public Usuario(Long id, TipoIdentificacion tipoIdentificacion, String identificacion, String contrasennaHash,
            String nombre, String primerApellido, String segundoApellido, String correoElectronico, Estado estado,
            Rol rol) {
        this.id = id;
        this.tipoIdentificacion = tipoIdentificacion;
        this.identificacion = identificacion;
        this.contrasennaHash = contrasennaHash;
        this.nombre = nombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.correoElectronico = correoElectronico;
        this.estado = estado;
        this.rol = rol;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoIdentificacion getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    public void setTipoIdentificacion(TipoIdentificacion tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getContrasennaHash() {
        return contrasennaHash;
    }

    public void setContrasennaHash(String contrasennaHash) {
        this.contrasennaHash = contrasennaHash;
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

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }
}