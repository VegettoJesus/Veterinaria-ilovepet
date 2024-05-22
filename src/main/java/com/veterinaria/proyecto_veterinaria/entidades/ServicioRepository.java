package com.veterinaria.proyecto_veterinaria.entidades;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface ServicioRepository extends PagingAndSortingRepository<Servicio,Long>{

    @Query("SELECT s FROM Servicio s JOIN Mascota m ON s.tipoMascota = m.especie WHERE m.id = :idMascota")
    List<Servicio> findByTipoMascota(@Param("idMascota") Long tipoMascota);
    
}
