package com.veterinaria.proyecto_veterinaria.entidadUsuario;

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
    public empleadoLogin findByEmail(String email);
    public Page<empleadoLogin> buscarPorFiltro(String filtro, Pageable pageable);
    public Page<empleadoLogin> findAllExcludingId1(Pageable pageable);
}
