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

import com.veterinaria.proyecto_veterinaria.entidadUsuario.empleadoLogin;

@Entity
@Table(name = "cita")
public class Citas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_empleado")
    private empleadoLogin empleado;

    @ManyToOne
    @JoinColumn(name = "id_propietario")
    private Propietario propietario;

    @ManyToOne
    @JoinColumn(name = "id_mascota")
    private Mascota mascota;

    @ManyToOne
    @JoinColumn(name = "id_servicio")
    private Servicio servicio;

    
    private LocalDateTime fechaRegistro = LocalDateTime.now();

    private String Observaciones;
    private String Tratamiento;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(iso = ISO.DATE)
    @NotNull
    private Date fechaCita;

    @NotEmpty
    private String horaCita;

    @NotEmpty
    private String Estado;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public empleadoLogin getEmpleado() {
        return empleado;
    }

    public void setEmpleado(empleadoLogin empleado) {
        this.empleado = empleado;
    }

    public Mascota getMascota() {
        return mascota;
    }

    public void setMascota(Mascota mascota) {
        this.mascota = mascota;
    }

    public Servicio getServicio() {
        return servicio;
    }

    public void setServicio(Servicio servicio) {
        this.servicio = servicio;
    }

    public String getObservaciones() {
        return Observaciones;
    }

    public void setObservaciones(String observaciones) {
        Observaciones = observaciones;
    }

    public Date getFechaCita() {
        return fechaCita;
    }

    public void setFechaCita(Date fechaCita) {
        this.fechaCita = fechaCita;
    }

    public String getHoraCita() {
        return horaCita;
    }

    public void setHoraCita(String horaCita) {
        this.horaCita = horaCita;
    }

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String estado) {
        Estado = estado;
    }

    public String getTratamiento() {
        return Tratamiento;
    }

    public void setTratamiento(String tratamiento) {
        Tratamiento = tratamiento;
    }
    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    @PrePersist
    public void asignarFechaRegistro(){
        fechaRegistro = LocalDateTime.now();
    }

    public Propietario getPropietario() {
        return propietario;
    }

    public void setPropietario(Propietario propietario) {
        this.propietario = propietario;
    }

    

    
    public Citas(Long id, empleadoLogin empleado, Propietario propietario, Mascota mascota, Servicio servicio,
            LocalDateTime fechaRegistro, String observaciones, String tratamiento, @NotNull Date fechaCita,
            @NotEmpty String horaCita, @NotEmpty String estado) {
        this.id = id;
        this.empleado = empleado;
        this.propietario = propietario;
        this.mascota = mascota;
        this.servicio = servicio;
        this.fechaRegistro = fechaRegistro;
        Observaciones = observaciones;
        Tratamiento = tratamiento;
        this.fechaCita = fechaCita;
        this.horaCita = horaCita;
        Estado = estado;
    }

    

    public Citas(empleadoLogin empleado, Propietario propietario, Mascota mascota, Servicio servicio,
            LocalDateTime fechaRegistro, String observaciones, String tratamiento, @NotNull Date fechaCita,
            @NotEmpty String horaCita, @NotEmpty String estado) {
        this.empleado = empleado;
        this.propietario = propietario;
        this.mascota = mascota;
        this.servicio = servicio;
        this.fechaRegistro = fechaRegistro;
        Observaciones = observaciones;
        Tratamiento = tratamiento;
        this.fechaCita = fechaCita;
        this.horaCita = horaCita;
        Estado = estado;
    }

    public Citas() {
    }


    @Override
    public String toString() {
        return "" + servicio + "";
    }

    

}
