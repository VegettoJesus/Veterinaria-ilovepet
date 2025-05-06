package com.veterinaria.proyecto_veterinaria.entidades;

import java.util.List;
import java.util.Map;

import com.veterinaria.proyecto_veterinaria.entidadUsuario.empleadoLogin;

public interface RegistroAsistenciaService {
    public List<RegistroAsistencia> findAll();
    public void save(RegistroAsistencia registroAsistencia);
    public RegistroAsistencia findOne(long id);
    public void delete(Long id);
    public Map<String, Long> obtenerResumenMensualPorEmpleado(Long empleadoId, int anio, int mes);
    public void deleteByEmpleado(empleadoLogin empleado); 
}