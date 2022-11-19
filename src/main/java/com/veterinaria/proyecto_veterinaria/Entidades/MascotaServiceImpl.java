package com.veterinaria.proyecto_veterinaria.Entidades;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service    
public class MascotaServiceImpl implements MascotaService{

    @Autowired
    private MascotaRepository mascotaRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Mascota> findAll() {
        return (List<Mascota>) mascotaRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Mascota> findAll(Pageable pageable) {
        return mascotaRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public void save(Mascota mascota) {
        mascotaRepository.save(mascota);
        
    }

    @Override
    @Transactional
    public Mascota findOne(long id) {
        return mascotaRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        mascotaRepository.deleteById(id);
        
    }
    
}
