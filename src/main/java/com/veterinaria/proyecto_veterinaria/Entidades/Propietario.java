package com.veterinaria.proyecto_veterinaria.Entidades;

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
    private String medio_Pago;
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
    public String getMedio_Pago() {
        return medio_Pago;
    }
    public void setMedio_Pago(String medio_Pago) {
        this.medio_Pago = medio_Pago;
    }
    public String getCorreo() {
        return correo;
    }
    public void setCorreo(String correo) {
        this.correo = correo;
    }
    public Propietario(Long id, @NotEmpty String nombre, @NotEmpty String direccion, @NotNull Long celular,
            @NotEmpty String medio_Pago, @NotEmpty String correo) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.celular = celular;
        this.medio_Pago = medio_Pago;
        this.correo = correo;
    }
    public Propietario(@NotEmpty String nombre, @NotEmpty String direccion, @NotNull Long celular,
            @NotEmpty String medio_Pago, @NotEmpty String correo) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.celular = celular;
        this.medio_Pago = medio_Pago;
        this.correo = correo;
    }
    public Propietario() {
    }
}
