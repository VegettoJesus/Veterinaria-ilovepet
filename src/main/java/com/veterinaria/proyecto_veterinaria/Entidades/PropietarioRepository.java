package com.veterinaria.proyecto_veterinaria.Entidades;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface PropietarioRepository extends PagingAndSortingRepository<Propietario,Long>{
    public Propietario findByNombre(String nombre);
}
