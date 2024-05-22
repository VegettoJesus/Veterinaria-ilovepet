package com.veterinaria.proyecto_veterinaria.entidades;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Service
public class Detalle_ventasServiceImpl implements Detalle_ventasService{

    @Autowired
    private Detalle_ventasRepository detalle_ventasRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Detalle_ventas> findAll() {
        return (List<Detalle_ventas>) detalle_ventasRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Detalle_ventas> findAll(Pageable pageable) {
        return detalle_ventasRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public void save(Detalle_ventas detalle_ventas) {
        detalle_ventasRepository.save(detalle_ventas);
    }

    @Override
    @Transactional
    public Detalle_ventas findOne(long id) {
        return detalle_ventasRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        detalle_ventasRepository.deleteById(id);
    }

    
}
