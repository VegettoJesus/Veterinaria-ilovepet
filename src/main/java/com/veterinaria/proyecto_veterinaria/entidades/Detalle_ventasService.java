package com.veterinaria.proyecto_veterinaria.entidades;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface Detalle_ventasService {
    public List<Detalle_ventas> findAll();
    public Page<Detalle_ventas> findAll(Pageable pageable);
    public void save(Detalle_ventas detalle_ventas);
    public Detalle_ventas findOne(long id);
    public void delete(Long id);
}
