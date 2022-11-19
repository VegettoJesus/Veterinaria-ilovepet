package com.veterinaria.proyecto_veterinaria.Entidades;

//import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
//import org.springframework.data.repository.query.Param;
public interface CitasRepository extends PagingAndSortingRepository<Citas,Long>{
    @Query(value="SELECT * FROM cita ORDER BY fecha_cita DESC", nativeQuery = true)
    public List<Citas> ordenarDesc();

    @Query(value="SELECT*FROM CITA WHERE cita.estado = 'Atendido'", nativeQuery = true)
    public List<Citas> ListaAtendidos();

    /*@Query(value="SELECT * FROM cita WHERE cita.hora_cita LIKE %:hora_cita% "
                    + "AND cita.id_servicio LIKE %:id_servicio% "
                    + "AND cita.fecha_cita LIKE %:fecha_cita%", nativeQuery = true)
    public List<Citas> existsReserva(@Param("hora_cita") String hora_cita,@Param("id_servicio") Servicio id_servicio,@Param("fecha_cita") Date fecha_cita); */
}
