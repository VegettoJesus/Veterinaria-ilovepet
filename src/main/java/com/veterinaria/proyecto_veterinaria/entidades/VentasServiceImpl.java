package com.veterinaria.proyecto_veterinaria.entidades;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VentasServiceImpl implements VentasService{

    @Autowired
    private VentasRepository ventasRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Ventas> findAll() {
        return (List<Ventas>) ventasRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Ventas> findAll(Pageable pageable) {
        return ventasRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public void save(Ventas ventas) {
        ventasRepository.save(ventas);
    }

    @Override
    @Transactional
    public Ventas findOne(long id) {
        return ventasRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        ventasRepository.deleteById(id);
    }

    @Override
    public String generarNumeroSerie(String tipoComprobante) {
        return ventasRepository.generarNumeroSerie(tipoComprobante);
    }

    @Override
    public String generarNumeroCorrelativo() {
        return ventasRepository.generarNumeroCorrelativo();
    }
    
}
