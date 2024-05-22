package com.veterinaria.proyecto_veterinaria.entidades;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface VentasRepository extends PagingAndSortingRepository<Ventas,Long>{

    @Query(value = "CALL generarNumeroSerie(?1)", nativeQuery = true)
    String generarNumeroSerie(String tipo_comprobante);

    @Procedure(name = "generarNumeroCorrelativo")
    String generarNumeroCorrelativo();
} 
