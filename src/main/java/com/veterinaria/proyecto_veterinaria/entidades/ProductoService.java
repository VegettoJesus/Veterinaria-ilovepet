package com.veterinaria.proyecto_veterinaria.entidades;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductoService {
    public List<Producto> findAll();
    public Page<Producto> findAll(Pageable pageable);
    public void save(Producto producto);
    public Producto findOne(long id);
    public void delete(Long id);
}
