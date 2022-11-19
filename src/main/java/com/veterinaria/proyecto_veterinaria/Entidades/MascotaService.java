package com.veterinaria.proyecto_veterinaria.Entidades;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MascotaService {
    public List<Mascota> findAll();
    public Page<Mascota> findAll(Pageable pageable);
    public void save(Mascota mascota);
    public Mascota findOne(long id);
    public void delete(Long id);
}
