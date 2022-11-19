package com.veterinaria.proyecto_veterinaria.Entidad_usuario;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface Empleado_Repositoriy extends PagingAndSortingRepository<Empleado_Login,Long>{
    public Empleado_Login findByEmail(String email);
}
