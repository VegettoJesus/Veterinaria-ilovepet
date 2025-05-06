package com.veterinaria.proyecto_veterinaria.entidades;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.veterinaria.proyecto_veterinaria.entidadUsuario.empleadoLogin;

public interface HorarioEmpleadoRepository extends JpaRepository<HorarioEmpleado, Long> {
    List<HorarioEmpleado> findByEmpleadoIdAndFechaInicioBetween(Long empleadoId, LocalDate inicio, LocalDate fin);
    List<HorarioEmpleado> findByEmpleadoId(Long empleadoId);
    void deleteByEmpleado(empleadoLogin empleado); 
}
