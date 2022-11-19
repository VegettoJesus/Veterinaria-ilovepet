package com.veterinaria.proyecto_veterinaria.Entidades;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface CategoriaRepository extends PagingAndSortingRepository<Categoria,Long>{
    public Categoria findByNombre(String nombre);
}
