package com.veterinaria.proyecto_veterinaria.entidades;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VentasService {
    public List<Ventas> findAll();
    public Page<Ventas> findAll(Pageable pageable);
    public void save(Ventas ventas);
    public Ventas findOne(long id);
    public void delete(Long id);
    public String generarNumeroSerie(String tipoComprobante);
    public String generarNumeroCorrelativo();
}
