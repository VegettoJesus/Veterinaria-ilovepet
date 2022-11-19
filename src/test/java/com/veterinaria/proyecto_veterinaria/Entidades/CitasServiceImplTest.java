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
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;

public class CitasServiceImplTest {
    @Mock
    private CitasRepository citasRepository;

   @InjectMocks
   private CitasServiceImpl citasServiceImpl;
 
  private Citas citas;

  @BeforeEach
  void setUp(){

    /*MockitoAnnotations.initMocks(this); */
    MockitoAnnotations.openMocks(this);

    citas = new Citas();
    citas.setId(5L);
  
  }
    @Test
    void testDelete() {
        final Long citasId =5L;  
        citasServiceImpl.delete(citasId);
  
        verify(citasRepository,times(1)).deleteById(citasId);
    }

    @Test
    void testFindAll() {
        when(citasRepository.findAll()).thenReturn(Arrays.asList(citas));
        assertNotNull(citasServiceImpl.findAll());
    }

    @Test
    void testFindAllPage() {
        when(citasRepository.findAll()).thenReturn(Arrays.asList(
            new Citas()
    ));

      @SuppressWarnings("unchecked")
      Page<Citas> citass = Mockito.mock(Page.class);
      Mockito.when(this.citasRepository.findAll((org.springframework.data.domain.Pageable) ArgumentMatchers.isA(Pageable.class))).thenReturn(citass);
      
    }

    @Test
    void testFindOne() {
        when(citasRepository.findAll()).thenReturn(Arrays.asList(citas));
        Assertions.assertNotEquals(citasServiceImpl.findOne(5L), citas);
    }

    @Test
    void testSave() {
        when(citasRepository.save(any(Citas.class))).thenReturn(citas);

        Citas saveCitas = citasRepository.save(citas);
        assertNotNull(saveCitas.getId());
    }
}