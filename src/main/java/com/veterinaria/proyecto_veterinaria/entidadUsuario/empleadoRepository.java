package com.veterinaria.proyecto_veterinaria.entidadUsuario;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface empleadoRepository extends PagingAndSortingRepository<empleadoLogin,Long>{
    public empleadoLogin findByEmail(String email);
}
