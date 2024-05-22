package com.veterinaria.proyecto_veterinaria.entidades;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface PropietarioRepository extends PagingAndSortingRepository<Propietario,Long>{
    public Propietario findByNombre(String nombre);
    public Propietario findByDni(String dni);
    @Query(value = "SELECT * FROM Propietario p WHERE p.dni LIKE %?1% ORDER BY p.dni ASC LIMIT 20", nativeQuery = true)
    List<Propietario> findFirst20ByDniLike(String dni);

    @Query(value = "SELECT COUNT(p.dni) > 0 FROM Propietario p WHERE p.dni = :dni", nativeQuery = true)
    int existeClientePorDNI(String dni);

}
