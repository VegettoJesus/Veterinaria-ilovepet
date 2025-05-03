package com.veterinaria.proyecto_veterinaria.entidadUsuario;

import org.springframework.data.domain.Page;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

public interface empleadoRepository extends PagingAndSortingRepository<empleadoLogin,Long>{
    public empleadoLogin findByEmail(String email);
    public empleadoLogin findByUsuario(String username);
    @Query("SELECT e FROM empleadoLogin e WHERE " +
           "LOWER(e.nombre) LIKE LOWER(CONCAT('%', :filtro, '%')) OR " +
           "LOWER(e.apellido) LIKE LOWER(CONCAT('%', :filtro, '%')) OR " +
           "LOWER(e.dni) LIKE LOWER(CONCAT('%', :filtro, '%')) OR " +
           "LOWER(e.usuario) LIKE LOWER(CONCAT('%', :filtro, '%')) OR " +
           "LOWER(e.email) LIKE LOWER(CONCAT('%', :filtro, '%'))")
    Page<empleadoLogin> buscarPorFiltro(@Param("filtro") String filtro, Pageable pageable);
}
