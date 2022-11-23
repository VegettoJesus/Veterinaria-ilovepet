package com.veterinaria.proyecto_veterinaria.entidades;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProductoRepository extends PagingAndSortingRepository<Producto,Long>{
    public Producto findByNombre(String nombre);
}
