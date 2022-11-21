package com.veterinaria.proyecto_veterinaria.Entidades;

//import static org.junit.Assert.assertNotNull;
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

import com.veterinaria.proyecto_veterinaria.entidades.Servicio;
import com.veterinaria.proyecto_veterinaria.entidades.ServicioRepository;
import com.veterinaria.proyecto_veterinaria.entidades.ServicioServiceImpl;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;

class ServicioServiceImplTest {
    @Mock
  private ServicioRepository servicioRepository;

  @InjectMocks
  private ServicioServiceImpl servicioServiceImpl;

  private Servicio servicio;

  @BeforeEach
  void setUp(){

    /*MockitoAnnotations.initMocks(this); */
    MockitoAnnotations.openMocks(this);

    servicio = new Servicio();
    servicio.setId(22L);
    servicio.setDescripcion(new String());
    servicio.setPrecio(50);
    servicio.setTipoMascota(new String());
  }
    @Test
    void testDelete() {
        final Long servicioId =22L;
  
        servicioServiceImpl.delete(servicioId);
        servicioServiceImpl.delete(servicioId);
        servicioServiceImpl.delete(servicioId);
        servicioServiceImpl.delete(servicioId);
  
        verify(servicioRepository,times(4)).deleteById(servicioId);

    }

    @Test
    void testFindAll() {
    when(servicioRepository.findAll()).thenReturn(Arrays.asList(servicio));
    assertNotNull(servicioServiceImpl.findAll());  

    }

    @Test
    void testFindAllPage() {
        when(servicioRepository.findAll()).thenReturn(Arrays.asList(
            new Servicio(22L, "Bano de Burbujas",50,"Perro")
        ));

      @SuppressWarnings("unchecked")
      Page<Servicio> servicios = Mockito.mock(Page.class);
      Mockito.when(this.servicioRepository.findAll((org.springframework.data.domain.Pageable) ArgumentMatchers.isA(Pageable.class))).thenReturn(servicios);
      
    }

    @Test
    void testFindOne() {
        when(servicioRepository.findAll()).thenReturn(Arrays.asList(servicio));
        Assertions.assertNotEquals(servicioServiceImpl.findOne(22L), servicio);
    }

    @Test
    void testSave() {
        when(servicioRepository.save(any(Servicio.class))).thenReturn(servicio);

        Servicio saveServicio = servicioRepository.save(servicio);
        assertNotNull(saveServicio.getId());
    }
}