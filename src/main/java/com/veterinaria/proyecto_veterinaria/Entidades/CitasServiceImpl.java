package com.veterinaria.proyecto_veterinaria.Entidades;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CitasServiceImpl implements CitasService{

    @Autowired
    private CitasRepository citasRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Citas> findAll() {
        return (List<Citas>) citasRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Citas> findAll(Pageable pageable) {
        return citasRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public void save(Citas citas) {
        citasRepository.save(citas);
        
    }

    @Override
    @Transactional
    public Citas findOne(long id) {
        return citasRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        citasRepository.deleteById(id);
    }

    @Override
    public List<Citas> ordenarDesc() {
        return citasRepository.ordenarDesc();
    }

    @Override
    public List<Citas> ListaAtendidos() {
        return citasRepository.ListaAtendidos();
    }
}
