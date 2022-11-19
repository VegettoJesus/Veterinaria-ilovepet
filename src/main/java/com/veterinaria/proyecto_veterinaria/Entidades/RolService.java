package com.veterinaria.proyecto_veterinaria.Entidades;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RolService {
    public List<Rol> findAll();
    public Page<Rol> findAll(Pageable pageable);
    public void save(Rol rol);
    public Rol findOne(long id);
    public void delete(Long id);
    
}
