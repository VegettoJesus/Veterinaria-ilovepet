package com.veterinaria.proyecto_veterinaria.entidades;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.veterinaria.proyecto_veterinaria.entidadUsuario.empleadoLogin;

import org.springframework.stereotype.Service;

@Service
public class RegistroAsistenciaServiceImpl implements RegistroAsistenciaService{

    @Autowired
    private RegistroAsistenciaRepository registroAsistenciaRepository;

    @Autowired
    private HorarioEmpleadoRepository horarioEmpleadoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<RegistroAsistencia> findAll() {
        return (List<RegistroAsistencia>) registroAsistenciaRepository.findAll();
    }

    @Override
    @Transactional
    public void save(RegistroAsistencia registroAsistencia) {
        registroAsistenciaRepository.save(registroAsistencia);
    }

    @Override
    @Transactional
    public RegistroAsistencia findOne(long id) {
        return registroAsistenciaRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        registroAsistenciaRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Map<String, Long> obtenerResumenMensualPorEmpleado(Long empleadoId, int anio, int mes) {
        List<RegistroAsistencia> asistencias = registroAsistenciaRepository.findAll()
            .stream()
            .filter(r -> r.getEmpleado().getId().equals(empleadoId))
            .collect(Collectors.toList());

        List<HorarioEmpleado> horarios = horarioEmpleadoRepository.findByEmpleadoId(empleadoId);

        Map<LocalDate, RegistroAsistencia> asistenciaPorFecha = asistencias.stream()
            .collect(Collectors.toMap(RegistroAsistencia::getFecha, r -> r, (a, b) -> a));

        Map<String, Long> conteo = new HashMap<>();
        LocalDate hoy = LocalDate.now();

        for (HorarioEmpleado horario : horarios) {
            LocalDate fecha = horario.getFechaInicio();

            if (fecha.getYear() != anio || fecha.getMonthValue() != mes) continue;
            if (horario.getHoraEntrada() == null) continue;

            RegistroAsistencia asistencia = asistenciaPorFecha.get(fecha);
            LocalTime horaEsperada = horario.getHoraEntrada();

            if (asistencia == null) {
                if (!fecha.isAfter(hoy)) {
                    conteo.put("Falta", conteo.getOrDefault("Falta", 0L) + 1);
                }
            } else if (asistencia.getHoraEntrada() == null) {
                conteo.put("Falta", conteo.getOrDefault("Falta", 0L) + 1);
            } else if (asistencia.getHoraEntrada().isAfter(horaEsperada)) {
                conteo.put("Tardanza", conteo.getOrDefault("Tardanza", 0L) + 1);
            } else {
                conteo.put("Asistencia", conteo.getOrDefault("Asistencia", 0L) + 1);
            }
        }

        return conteo;
    }

    @Override
    @Transactional
    public void deleteByEmpleado(empleadoLogin empleado) {
        registroAsistenciaRepository.deleteByEmpleado(empleado);
    }
}
