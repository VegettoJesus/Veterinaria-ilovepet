package com.veterinaria.proyecto_veterinaria.Entidad_usuario;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface empleadoRepository extends PagingAndSortingRepository<empleadoLogin,Long>{
    public empleadoLogin findByEmail(String email);
}
