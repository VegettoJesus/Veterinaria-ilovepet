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
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;



public class MascotaServiceImplTest {
    @Mock
    private MascotaRepository mascotaRepository;

   @InjectMocks
   private MascotaServiceImpl mascotaServiceImpl;
 
  private Mascota mascota;

  @BeforeEach
  void setUp(){

    /*MockitoAnnotations.initMocks(this); */
    MockitoAnnotations.openMocks(this);

    mascota = new Mascota();
    mascota.setId(10L);
    mascota.setNombre("Bulbasaur");
    mascota.setEspecie(new String());
    mascota.setRaza(new String());
    mascota.setEdad(10);
    mascota.setEstatura(110);
    mascota.setSexo("Macho");
    mascota.setColor("Marron");
    mascota.setPeso(50);

  }

    @Test
    void testDelete() {
      final Long mascotaId =12L;
      /*final String mascotaNombre = "Bulbasaur";
      final String mascotaEspecie = "Perro";
      final String mascotaRaza = "Pitbull";
      final int mascotaEdad = 10;*/

      mascotaServiceImpl.delete(mascotaId);

      verify(mascotaRepository,times(1)).deleteById(mascotaId);
    }

    @Test
    void testFindAll() {
        when(mascotaRepository.findAll()).thenReturn(Arrays.asList(mascota));
        assertNotNull(mascotaServiceImpl.findAll());
    }

    @Test
    void testFindAll2Page() {
      when(mascotaRepository.findAll()).thenReturn(Arrays.asList(
        new Mascota(12L, "Bulbasaur", "Perro", "Pitbull", 5, 100, null, "Masculino", "Blanco", 70, null)
      ));

      @SuppressWarnings("unchecked")
      Page<Mascota> mascotas = Mockito.mock(Page.class);
      Mockito.when(this.mascotaRepository.findAll((org.springframework.data.domain.Pageable) ArgumentMatchers.isA(Pageable.class))).thenReturn(mascotas);
    }

    @Test
    void testFindOne() {
        when(mascotaRepository.findAll()).thenReturn(Arrays.asList(mascota));
        Assertions.assertNotEquals(mascotaServiceImpl.findOne(12L), mascota);
    }

    @Test
    void testSave() {
      when(mascotaRepository.save(any(Mascota.class))).thenReturn(mascota);

      Mascota saveMascota = mascotaRepository.save(mascota);
      assertNotNull(saveMascota.getId());

    }
}
