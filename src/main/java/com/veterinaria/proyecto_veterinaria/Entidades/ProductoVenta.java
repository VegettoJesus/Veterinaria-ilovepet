package com.veterinaria.proyecto_veterinaria.Entidades;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class ProductoVenta extends Producto{
    private float cantidad;

    public ProductoVenta(Long id, @NotEmpty String codigo, @NotEmpty String nombre, @NotNull float precio,
            @NotNull Long stock, float cantidad) {
        super(id, codigo, nombre, precio, stock);
        this.cantidad = cantidad;
    }

    public ProductoVenta(@NotEmpty String codigo, @NotEmpty String nombre, @NotNull float precio, @NotNull Long stock,
            float cantidad) {
        super(codigo, nombre, precio, stock);
        this.cantidad = cantidad;
    }

    public void aumentarCantidad(){
        this.cantidad++;
    }

    public float getCantidad(){
        return cantidad;
    }

    public float getTotal(){
        return this.getPrecio() * this.cantidad;
    }
    
}
