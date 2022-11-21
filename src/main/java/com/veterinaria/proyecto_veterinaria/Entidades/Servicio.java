package com.veterinaria.proyecto_veterinaria.entidades;

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
    private String tipoMascota;

    
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

    public String getTipoMascota() {
        return tipoMascota;
    }


    public void setTipoMascota(String tipoMascota) {
        this.tipoMascota = tipoMascota;
    }
    

    public Servicio(Long id, @NotEmpty String descripcion, @NotNull float precio, @NotEmpty String tipoMascota) {
        this.id = id;
        this.descripcion = descripcion;
        this.precio = precio;
        this.tipoMascota = tipoMascota;
    }

    
    public Servicio(@NotEmpty String descripcion, @NotNull float precio, @NotEmpty String tipoMascota) {
        this.descripcion = descripcion;
        this.precio = precio;
        this.tipoMascota = tipoMascota;
    }


    public Servicio() {
    }


    @Override
    public String toString() {
        return descripcion;
    }


    

    

}
