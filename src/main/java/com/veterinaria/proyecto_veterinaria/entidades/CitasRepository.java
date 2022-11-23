package com.veterinaria.proyecto_veterinaria.entidades;


import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
public interface CitasRepository extends PagingAndSortingRepository<Citas,Long>{
    @Query(value="SELECT * FROM cita ORDER BY fecha_cita DESC", nativeQuery = true)
    public List<Citas> ordenarDesc();

    @Query(value="SELECT*FROM CITA WHERE cita.estado = 'Atendido'", nativeQuery = true)
    public List<Citas> listaAtendidos();
}
