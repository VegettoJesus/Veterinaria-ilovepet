package com.veterinaria.proyecto_veterinaria.entidadUsuario;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.veterinaria.proyecto_veterinaria.entidades.Rol;

@Entity
@Table(name = "empleado_login", uniqueConstraints = @UniqueConstraint(columnNames = { "usuario", "email" }))
public class empleadoLogin implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long dni;

    @NotEmpty
    @Column(name = "nombre")
    private String nombre;

    @NotEmpty
    @Column(name = "apellido")
    private String apellido;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(iso = ISO.DATE)
    @NotNull
    private Date fechaNacimiento;

    @NotNull
    private Integer celular;

    @Email
    private String email;

    @NotEmpty
    private String direccion;

    @NotEmpty
    private String usuario;
    @NotEmpty
    private String password;
    @NotEmpty
    private String sexo;
    @NotEmpty
    @ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(name = "usuario_roles", joinColumns = @JoinColumn(name = "usuario_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "rol_id", referencedColumnName = "id"))
    private List<Rol> tipoRol;

    private String imagen;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDni() {
        return dni;
    }

    public void setDni(Long dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Integer getCelular() {
        return celular;
    }

    public void setCelular(Integer celular) {
        this.celular = celular;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public List<Rol> getTipoRol() {
        return tipoRol;
    }

    public void setTipoRol(List<Rol> tipoRol) {
        this.tipoRol = tipoRol;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public empleadoLogin(Long id, @NotNull Long dni, @NotEmpty String nombre, @NotEmpty String apellido,
            @NotNull Date fechaNacimiento, @NotNull Integer celular, @Email String email, @NotEmpty String direccion,
            @NotEmpty String usuario, @NotEmpty String password, @NotEmpty String sexo, @NotEmpty List<Rol> tipoRol, String imagen) {
        this.id = id;
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
        this.celular = celular;
        this.email = email;
        this.direccion = direccion;
        this.usuario = usuario;
        this.password = password;
        this.sexo = sexo;
        this.tipoRol = tipoRol;
        this.imagen = imagen;
    }

    
    public empleadoLogin(@NotNull Long dni, @NotEmpty String nombre, @NotEmpty String apellido,
            @NotNull Date fechaNacimiento, @NotNull Integer celular, @Email String email, @NotEmpty String direccion,
            @NotEmpty String usuario, @NotEmpty String password, @NotEmpty String sexo, @NotEmpty List<Rol> tipoRol, String imagen) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
        this.celular = celular;
        this.email = email;
        this.direccion = direccion;
        this.usuario = usuario;
        this.password = password;
        this.sexo = sexo;
        this.tipoRol = tipoRol;
        this.imagen = imagen;
    }

    

    public empleadoLogin(@NotNull Long dni, @NotEmpty String nombre, @NotEmpty String apellido,
            @NotNull Date fechaNacimiento, @NotNull Integer celular, @Email String email, @NotEmpty String direccion,
            @NotEmpty String usuario, @NotEmpty String password, @NotEmpty String sexo, String imagen) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
        this.celular = celular;
        this.email = email;
        this.direccion = direccion;
        this.usuario = usuario;
        this.password = password;
        this.sexo = sexo;
        this.imagen = imagen;
    }

    public empleadoLogin(@NotEmpty String password) {
        this.password = password;
    }

    public empleadoLogin(@Email String email, @NotEmpty String password, @NotEmpty List<Rol> tipoRol) {
        this.email = email;
        this.password = password;
        this.tipoRol = tipoRol;
    }

    public empleadoLogin() {
    }

    @Override
    public String toString() {
        return nombre + apellido;
    }


    

    

    

}
