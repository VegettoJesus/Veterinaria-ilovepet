package com.veterinaria.proyecto_veterinaria.Entidades;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;

import com.veterinaria.proyecto_veterinaria.entidades.Rol;
import com.veterinaria.proyecto_veterinaria.entidades.RolRepository;
import com.veterinaria.proyecto_veterinaria.entidades.RolServiceImpl;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;

/*import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;*/

class RolServiceImplTest {

    @Mock
    private RolRepository rolRepository;


    @InjectMocks
    private RolServiceImpl rolServiceImpl;
    private Rol rol;

    
    @BeforeEach
   void setUp(){
    /*MockitoAnnotations.initMocks(this); */
    MockitoAnnotations.openMocks(this);
    rol = new Rol();
    rol.setId(10L);
    rol.setNombre("Administrador");
  }
    @Test
    void testDelete() {
    final Long rolId =10L;
  
      rolServiceImpl.delete(rolId);

      verify(rolRepository,times(1)).deleteById(rolId);
    }

    @Test
    void testFindAll() {
        when(rolRepository.findAll()).thenReturn(Arrays.asList(rol));
        assertNotNull(rolServiceImpl.findAll());

    }

    @Test
    void testFindAll2Page() {
        /*when(rolRepository.findAll()).thenReturn(Arrays.asList(() -> ));
        assertNotNull(rolServiceImpl.findAll());*/
        when(rolRepository.findAll()).thenReturn(Arrays.asList(
        new Rol(10L,"Administrador")
        ));

      @SuppressWarnings("unchecked")
      Page<Rol> roles = Mockito.mock(Page.class);
      Mockito.when(this.rolRepository.findAll((org.springframework.data.domain.Pageable) ArgumentMatchers.isA(Pageable.class))).thenReturn(roles);
    }

    @Test
    void testFindOne() {

        when(rolRepository.findAll()).thenReturn(Arrays.asList(rol));
       Assertions.assertNotEquals(rolServiceImpl.findOne(44L), rol);

    }

    @Test
    void testSave() {
        when(rolRepository.save(any(Rol.class))).thenReturn(rol);

        Rol saveRol = rolRepository.save(rol);
        assertNotNull(saveRol.getId());
    }
}
