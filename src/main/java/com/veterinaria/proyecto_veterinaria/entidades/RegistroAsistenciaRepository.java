package com.veterinaria.proyecto_veterinaria.entidades;

import org.springframework.data.jpa.repository.JpaRepository;

import com.veterinaria.proyecto_veterinaria.entidadUsuario.empleadoLogin;

public interface RegistroAsistenciaRepository extends JpaRepository<RegistroAsistencia, Long> {
    public void deleteByEmpleado(empleadoLogin empleado); 
}
