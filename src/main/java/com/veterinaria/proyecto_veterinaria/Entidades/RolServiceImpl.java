package com.veterinaria.proyecto_veterinaria.Entidades;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RolServiceImpl implements RolService{
    @Autowired
    private RolRepository rolRepository;


    @Override
    @Transactional(readOnly = true)
    public List<Rol> findAll() {
        return (List<Rol>) rolRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Rol> findAll(Pageable pageable) {
        return rolRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public void save(Rol rol) {
        rolRepository.save(rol);
        
    }

    @Override
    @Transactional
    public Rol findOne(long id) {
        return rolRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        rolRepository.deleteById(id);
        
    }
    
}
