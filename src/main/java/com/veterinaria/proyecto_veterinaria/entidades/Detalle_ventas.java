package com.veterinaria.proyecto_veterinaria.entidades;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "detalle_ventas")
public class Detalle_ventas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_ventas")
    private Ventas ventas;

    @ManyToOne
    @JoinColumn(name = "id_producto")
    private Producto producto;

    @ManyToOne
    @JoinColumn(name = "id_servicio")
    private Servicio servicio;

    @NotNull
    private Integer cantidad;

    private float dscto;

    private float subPrecioVenta;

    @NotNull
    private float precioVenta;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Ventas getVentas() {
        return ventas;
    }

    public void setVentas(Ventas ventas) {
        this.ventas = ventas;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public float getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(float precioVenta) {
        this.precioVenta = precioVenta;
    }

    public Servicio getServicio() {
        return servicio;
    }

    public void setServicio(Servicio servicio) {
        this.servicio = servicio;
    }

    public float getDscto() {
        return dscto;
    }

    public void setDscto(float dscto) {
        this.dscto = dscto;
    }

    public float getSubPrecioVenta() {
        return subPrecioVenta;
    }

    public void setSubPrecioVenta(float subPrecioVenta) {
        this.subPrecioVenta = subPrecioVenta;
    }

    public Detalle_ventas(Long id, Ventas ventas, Producto producto, Servicio servicio, @NotNull Integer cantidad,
            float dscto, float subPrecioVenta, @NotNull float precioVenta) {
        this.id = id;
        this.ventas = ventas;
        this.producto = producto;
        this.servicio = servicio;
        this.cantidad = cantidad;
        this.dscto = dscto;
        this.subPrecioVenta = subPrecioVenta;
        this.precioVenta = precioVenta;
    }

    public Detalle_ventas() {
        
    }
}
