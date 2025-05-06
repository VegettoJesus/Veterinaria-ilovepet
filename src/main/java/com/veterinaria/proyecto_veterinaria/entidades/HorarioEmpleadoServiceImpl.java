package com.veterinaria.proyecto_veterinaria.entidades;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.veterinaria.proyecto_veterinaria.entidadUsuario.empleadoLogin;

import org.springframework.stereotype.Service;

@Service
public class HorarioEmpleadoServiceImpl implements HorarioEmpleadoService{

    @Autowired
    private HorarioEmpleadoRepository horarioEmpleadoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<HorarioEmpleado> findAll() {
        return (List<HorarioEmpleado>) horarioEmpleadoRepository.findAll();
    }

    @Override
    @Transactional
    public void save(HorarioEmpleado horarioEmpleado) {
        horarioEmpleadoRepository.save(horarioEmpleado);
    }

    @Override
    @Transactional
    public HorarioEmpleado findOne(long id) {
        return horarioEmpleadoRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        horarioEmpleadoRepository.deleteById(id);
    }

    @Override
    public List<HorarioEmpleado> findByEmpleadoIdAndFechaInicioBetween(Long empleadoId, LocalDate inicio, LocalDate fin) {
        return horarioEmpleadoRepository.findByEmpleadoIdAndFechaInicioBetween(empleadoId, inicio, fin);
    }

    @Override
    public List<HorarioEmpleado> findByEmpleadoId(Long empleadoId) {
        return horarioEmpleadoRepository.findByEmpleadoId(empleadoId);
    }

    @Override
    @Transactional
    public void deleteByEmpleado(empleadoLogin empleado) {
        horarioEmpleadoRepository.deleteByEmpleado(empleado);
    }
}
