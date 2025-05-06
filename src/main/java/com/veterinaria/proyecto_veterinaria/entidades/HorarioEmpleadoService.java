package com.veterinaria.proyecto_veterinaria.entidades;

import java.time.LocalDate;
import java.util.List;

import com.veterinaria.proyecto_veterinaria.entidadUsuario.empleadoLogin;

public interface HorarioEmpleadoService {
    public List<HorarioEmpleado> findAll();
    public void save(HorarioEmpleado horarioEmpleado);
    public HorarioEmpleado findOne(long id);
    public void delete(Long id);
    public List<HorarioEmpleado> findByEmpleadoIdAndFechaInicioBetween(Long empleadoId, LocalDate inicio, LocalDate fin);
    public List<HorarioEmpleado> findByEmpleadoId(Long empleadoId);
    public void deleteByEmpleado(empleadoLogin empleado);
}