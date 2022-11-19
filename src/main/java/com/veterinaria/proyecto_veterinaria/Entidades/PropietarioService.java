package com.veterinaria.proyecto_veterinaria.Entidades;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PropietarioService {
    public List<Propietario> findAll();
    public Page<Propietario> findAll(Pageable pageable);
    public void save(Propietario propietario);
    public Propietario findOne(long id);
    public void delete(Long id);
}
