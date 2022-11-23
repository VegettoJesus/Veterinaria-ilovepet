package com.veterinaria.proyecto_veterinaria.entidades;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

public interface CategoriaService {
    public List<Categoria> findAll();
    public Page<Categoria> findAll(Pageable pageable);
    public void save(Categoria categoria);
    public Categoria findOne(long id);
    public void delete(Long id);
}
