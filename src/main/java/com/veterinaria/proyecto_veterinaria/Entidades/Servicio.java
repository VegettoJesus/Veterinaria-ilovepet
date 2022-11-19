package com.veterinaria.proyecto_veterinaria.Entidades;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "servicio")
public class Servicio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String descripcion;

    @NotNull
    private float precio;

    @NotEmpty
    private String tipo_mascota;

    
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


    public float getPrecio() {
        return precio;
    }


    public void setPrecio(float precio) {
        this.precio = precio;
    }


    public String getTipo_mascota() {
        return tipo_mascota;
    }


    public void setTipo_mascota(String tipo_mascota) {
        this.tipo_mascota = tipo_mascota;
    }


    public Servicio(Long id, @NotEmpty String descripcion, @NotNull float precio, @NotEmpty String tipo_mascota) {
        this.id = id;
        this.descripcion = descripcion;
        this.precio = precio;
        this.tipo_mascota = tipo_mascota;
    }

    
    public Servicio(@NotEmpty String descripcion, @NotNull float precio, @NotEmpty String tipo_mascota) {
        this.descripcion = descripcion;
        this.precio = precio;
        this.tipo_mascota = tipo_mascota;
    }


    public Servicio() {
    }


    @Override
    public String toString() {
        return descripcion;
    }

    

}
