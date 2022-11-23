package com.veterinaria.proyecto_veterinaria.entidades;

import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
@Entity
@Table(name = "producto")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String codigo;

    @NotEmpty
    private String nombre;

    @NotEmpty
    private String descripcion;
    
    @NotNull
    private float precio;
    
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(iso = ISO.DATE)
    @NotNull
    private Date fechaVencimiento;

    private LocalDateTime fechaRegistro = LocalDateTime.now();

    @NotEmpty
    private String marca;

    @NotEmpty
    private String nombreProveedor;

    @NotNull
    private Long rucProveedor;

    @NotNull
    private Long stock;

    @ManyToOne
    @JoinColumn(name = "id_categoria")
    private Categoria categoria;

    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getNombreProveedor() {
        return nombreProveedor;
    }

    public void setNombreProveedor(String nombreProveedor) {
        this.nombreProveedor = nombreProveedor;
    }

    public Long getRucProveedor() {
        return rucProveedor;
    }

    public void setRucProveedor(Long rucProveedor) {
        this.rucProveedor = rucProveedor;
    }

    public Long getStock() {
        return stock;
    }

    public void setStock(Long stock) {
        this.stock = stock;
    }

    public Producto() {
    }
    
    public boolean sinStock(){
        return this.stock <= 0;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public void restarStock(Long stock){
        this.stock -= stock;
    }

    @PrePersist
    public void asignarFechaRegistro(){
        fechaRegistro = LocalDateTime.now();
    }

    public Producto(Long id, @NotEmpty String codigo, @NotEmpty String nombre, @NotEmpty String descripcion,
            @NotNull float precio, @NotNull Date fecha_Vencimiento, LocalDateTime fechaRegistro,
            @NotEmpty String marca, @NotEmpty String nombreProveedor, @NotNull Long rucProveedor, @NotNull Long stock,
            Categoria categoria) {
        this.id = id;
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.fechaVencimiento = fecha_Vencimiento;
        this.fechaRegistro = fechaRegistro;
        this.marca = marca;
        this.nombreProveedor = nombreProveedor;
        this.rucProveedor = rucProveedor;
        this.stock = stock;
        this.categoria = categoria;
    }


    public Producto(@NotEmpty String codigo, @NotEmpty String nombre, @NotEmpty String descripcion,
            @NotNull float precio, @NotNull Date fechaVencimiento, LocalDateTime fechaRegistro,
            @NotEmpty String marca, @NotEmpty String nombreProveedor, @NotNull Long rucProveedor, @NotNull Long stock,
            Categoria categoria) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.fechaVencimiento = fechaVencimiento;
        this.fechaRegistro = fechaRegistro;
        this.marca = marca;
        this.nombreProveedor = nombreProveedor;
        this.rucProveedor = rucProveedor;
        this.stock = stock;
        this.categoria = categoria;
    }

    public Producto(Long id, @NotEmpty String codigo, @NotEmpty String nombre, @NotNull float precio,
            @NotNull Long stock) {
        this.id = id;
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
    }

    public Producto(@NotEmpty String codigo, @NotEmpty String nombre, @NotNull float precio, @NotNull Long stock) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
    }

    
    
}
