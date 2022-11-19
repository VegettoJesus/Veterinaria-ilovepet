package com.veterinaria.proyecto_veterinaria.Entidades;


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

import com.veterinaria.proyecto_veterinaria.Entidad_usuario.Empleado_Login;

@Entity
@Table(name = "cita")
public class Citas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_empleado")
    private Empleado_Login empleado;

    @ManyToOne
    @JoinColumn(name = "id_propietario")
    private Propietario propietario;

    @ManyToOne
    @JoinColumn(name = "id_mascota")
    private Mascota mascota;

    @ManyToOne
    @JoinColumn(name = "id_servicio")
    private Servicio servicio;

    
    private LocalDateTime fecha_registro = LocalDateTime.now();

    private String Observaciones;
    private String Tratamiento;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(iso = ISO.DATE)
    @NotNull
    private Date fecha_cita;

    @NotEmpty
    private String hora_cita;

    @NotEmpty
    private String Estado;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Empleado_Login getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado_Login empleado) {
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

    public LocalDateTime getFecha_registro() {
        return fecha_registro;
    }

    public void setFecha_registro(LocalDateTime fecha_registro) {
        this.fecha_registro = fecha_registro;
    }

    public String getObservaciones() {
        return Observaciones;
    }

    public void setObservaciones(String observaciones) {
        Observaciones = observaciones;
    }

    public Date getFecha_cita() {
        return fecha_cita;
    }

    public void setFecha_cita(Date fecha_cita) {
        this.fecha_cita = fecha_cita;
    }

    public String getHora_cita() {
        return hora_cita;
    }

    public void setHora_cita(String hora_cita) {
        this.hora_cita = hora_cita;
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

    @PrePersist
    public void asignarFechaRegistro(){
        fecha_registro = LocalDateTime.now();
    }

    public Propietario getPropietario() {
        return propietario;
    }

    public void setPropietario(Propietario propietario) {
        this.propietario = propietario;
    }

    

    
    public Citas(Long id, Empleado_Login empleado, Propietario propietario, Mascota mascota, Servicio servicio,
            LocalDateTime fecha_registro, String observaciones, String tratamiento, @NotNull Date fecha_cita,
            @NotEmpty String hora_cita, @NotEmpty String estado) {
        this.id = id;
        this.empleado = empleado;
        this.propietario = propietario;
        this.mascota = mascota;
        this.servicio = servicio;
        this.fecha_registro = fecha_registro;
        Observaciones = observaciones;
        Tratamiento = tratamiento;
        this.fecha_cita = fecha_cita;
        this.hora_cita = hora_cita;
        Estado = estado;
    }

    

    public Citas(Empleado_Login empleado, Propietario propietario, Mascota mascota, Servicio servicio,
            LocalDateTime fecha_registro, String observaciones, String tratamiento, @NotNull Date fecha_cita,
            @NotEmpty String hora_cita, @NotEmpty String estado) {
        this.empleado = empleado;
        this.propietario = propietario;
        this.mascota = mascota;
        this.servicio = servicio;
        this.fecha_registro = fecha_registro;
        Observaciones = observaciones;
        Tratamiento = tratamiento;
        this.fecha_cita = fecha_cita;
        this.hora_cita = hora_cita;
        Estado = estado;
    }

    public Citas() {
    }


    @Override
    public String toString() {
        return "" + servicio + "";
    }

}
