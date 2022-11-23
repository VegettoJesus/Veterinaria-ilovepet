package com.veterinaria.proyecto_veterinaria.entidades;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PropietarioServiceImpl implements PropietarioService {

    @Autowired
    private PropietarioRepository propietarioRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Propietario> findAll() {
        return (List<Propietario>) propietarioRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Propietario> findAll(Pageable pageable) {
        return propietarioRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public void save(Propietario propietario) {
        propietarioRepository.save(propietario);
        
    }

    @Override
    @Transactional
    public Propietario findOne(long id) {
        return propietarioRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        propietarioRepository.deleteById(id);
        
    }
    
}
