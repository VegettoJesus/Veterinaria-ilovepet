package com.veterinaria.proyecto_veterinaria.entidades;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface MascotaRepository extends PagingAndSortingRepository<Mascota,Long>{
    public Mascota findByNombre(String nombre);
    public List<Mascota> findByPropietario(Propietario propietario);
}
