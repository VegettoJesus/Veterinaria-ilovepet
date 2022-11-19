package com.veterinaria.proyecto_veterinaria.Entidades;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "rol")
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String nombre;

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

    public Rol(Long id, @NotEmpty String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Rol(@NotEmpty String nombre) {
        this.nombre = nombre;
    }

    public Rol(Long id) {
        this.id = id;
    }


    public Rol() {
    }

}
