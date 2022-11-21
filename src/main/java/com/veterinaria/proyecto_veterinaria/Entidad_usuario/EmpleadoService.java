package com.veterinaria.proyecto_veterinaria.Entidad_usuario;

import java.util.List;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface EmpleadoService extends UserDetailsService{
    public List<empleadoLogin> findAll();
    public Page<empleadoLogin> findAll(Pageable pageable);
    public void save(empleadoLogin empleadoLogin);
    public empleadoLogin findOne(long id);
    public void delete(Long id);
}
