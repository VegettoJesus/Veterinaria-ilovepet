package com.veterinaria.proyecto_veterinaria.entidades;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface RolRepository extends PagingAndSortingRepository<Rol,Long>{
    public Rol findByNombre(String nombre);
}
