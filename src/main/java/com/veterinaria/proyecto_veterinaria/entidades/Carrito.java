package com.veterinaria.proyecto_veterinaria.entidades;

public class Carrito {
    private int IdProducto;
    private String Nombre;
    private float Precio;
    private int Cantidad;
    private float dscto;
    private float Total;

    public float getDscto() {
        return dscto;
    }

    public void setDscto(float dscto) {
        this.dscto = dscto;
    }

    public int getIdProducto() {
        return IdProducto;
    }

    public void setIdProducto(int idProducto) {
        IdProducto = idProducto;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public float getPrecio() {
        return Precio;
    }

    public void setPrecio(float precio) {
        Precio = precio;
    }

    public int getCantidad() {
        return Cantidad;
    }

    public void setCantidad(int cantidad) {
        Cantidad = cantidad;
    }

    public float getTotal() {
        return Total;
    }

    public void setTotal(float total) {
        Total = total;
    }

}
