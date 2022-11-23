package com.veterinaria.proyecto_veterinaria.entidades;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface PropietarioRepository extends PagingAndSortingRepository<Propietario,Long>{
    public Propietario findByNombre(String nombre);
}
