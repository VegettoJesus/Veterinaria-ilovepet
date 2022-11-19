package com.veterinaria.proyecto_veterinaria.Entidades;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface MascotaRepository extends PagingAndSortingRepository<Mascota,Long>{
    public Mascota findByNombre(String nombre);
}
