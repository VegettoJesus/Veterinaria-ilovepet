package com.veterinaria.proyecto_veterinaria.entidades;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.veterinaria.proyecto_veterinaria.entidadUsuario.empleadoLogin;

@Entity
@Table(name = "ventas")
public class Ventas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String dni_cliente;

    @ManyToOne
    @JoinColumn(name = "id_empleado")
    private empleadoLogin empleado;

    @NotEmpty
    private String tipoComprobante;

    @NotEmpty
    private String numeroCorrelativo;

    @NotEmpty
    private String numeroSerie;

    private LocalDateTime fechaVentas = LocalDateTime.now();

    private float igv;

    private float submonto;

    @NotNull
    private float monto;

    @NotNull
    private Integer estado;

    public float getIgv() {
        return igv;
    }

    public void setIgv(float igv) {
        this.igv = igv;
    }

    public float getSubmonto() {
        return submonto;
    }

    public void setSubmonto(float submonto) {
        this.submonto = submonto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDni_cliente() {
        return dni_cliente;
    }

    public void setDni_cliente(String dni_cliente) {
        this.dni_cliente = dni_cliente;
    }

    public empleadoLogin getEmpleado() {
        return empleado;
    }

    public void setEmpleado(empleadoLogin empleado) {
        this.empleado = empleado;
    }

    public String getTipoComprobante() {
        return tipoComprobante;
    }

    public void setTipoComprobante(String tipoComprobante) {
        this.tipoComprobante = tipoComprobante;
    }

    public String getNumeroCorrelativo() {
        return numeroCorrelativo;
    }

    public void setNumeroCorrelativo(String numeroCorrelativo) {
        this.numeroCorrelativo = numeroCorrelativo;
    }

    public String getNumeroSerie() {
        return numeroSerie;
    }

    public void setNumeroSerie(String numeroSerie) {
        this.numeroSerie = numeroSerie;
    }

    public LocalDateTime getFechaVentas() {
        return fechaVentas;
    }

    public void setFechaVentas(LocalDateTime fechaVentas) {
        this.fechaVentas = fechaVentas;
    }

    public float getMonto() {
        return monto;
    }

    public void setMonto(float monto) {
        this.monto = monto;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public Ventas(Long id, @NotEmpty String dni_cliente, empleadoLogin empleado, @NotEmpty String tipoComprobante,
            @NotEmpty String numeroCorrelativo, @NotEmpty String numeroSerie, LocalDateTime fechaVentas, float igv,
            float submonto, @NotNull float monto, @NotNull Integer estado) {
        this.id = id;
        this.dni_cliente = dni_cliente;
        this.empleado = empleado;
        this.tipoComprobante = tipoComprobante;
        this.numeroCorrelativo = numeroCorrelativo;
        this.numeroSerie = numeroSerie;
        this.fechaVentas = fechaVentas;
        this.igv = igv;
        this.submonto = submonto;
        this.monto = monto;
        this.estado = estado;
    }

    public Ventas() {
        
    }

}
