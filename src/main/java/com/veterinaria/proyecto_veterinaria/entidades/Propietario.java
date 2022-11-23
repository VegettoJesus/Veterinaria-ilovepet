package com.veterinaria.proyecto_veterinaria.entidades;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "propietario")
public class Propietario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String nombre;
    @NotEmpty
    private String direccion;
    @NotNull
    private Long celular;
    @NotEmpty
    private String medioPago;
    @NotEmpty
    private String correo;
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
    public String getDireccion() {
        return direccion;
    }
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    public Long getCelular() {
        return celular;
    }
    public void setCelular(Long celular) {
        this.celular = celular;
    }
    public String getMedioPago() {
        return medioPago;
    }
    public void setMedioPago(String medioPago) {
        this.medioPago = medioPago;
    }
    public String getCorreo() {
        return correo;
    }
    public void setCorreo(String correo) {
        this.correo = correo;
    }
    public Propietario(Long id, @NotEmpty String nombre, @NotEmpty String direccion, @NotNull Long celular,
            @NotEmpty String medioPago, @NotEmpty String correo) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.celular = celular;
        this.medioPago = medioPago;
        this.correo = correo;
    }
    public Propietario(@NotEmpty String nombre, @NotEmpty String direccion, @NotNull Long celular,
            @NotEmpty String medioPago, @NotEmpty String correo) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.celular = celular;
        this.medioPago = medioPago;
        this.correo = correo;
    }
    public Propietario() {
    }
    
}
