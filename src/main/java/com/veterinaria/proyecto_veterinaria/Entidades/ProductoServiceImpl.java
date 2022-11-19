package com.veterinaria.proyecto_veterinaria.Entidades;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductoServiceImpl implements ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Producto> findAll() {
        return (List<Producto>) productoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Producto> findAll(Pageable pageable) {
        return productoRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public void save(Producto producto) {
        productoRepository.save(producto);
        
    }

    @Override
    @Transactional
    public Producto findOne(long id) {
        return productoRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        productoRepository.deleteById(id);
        
    }
    
}
