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
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;

import com.veterinaria.proyecto_veterinaria.entidades.Propietario;
import com.veterinaria.proyecto_veterinaria.entidades.PropietarioRepository;
import com.veterinaria.proyecto_veterinaria.entidades.PropietarioServiceImpl;

class PropietarioServiceImplTest {
  /*Proviene del PropietarioServiceImpl porque usa el repositorio */
  @Mock
  private PropietarioRepository propietarioRepository;

  @InjectMocks
  private PropietarioServiceImpl propietarioServiceImpl;

  private Propietario propietario;

  @BeforeEach
  void setUp(){

    /*MockitoAnnotations.initMocks(this); */
    MockitoAnnotations.openMocks(this);

    propietario = new Propietario();
    propietario.setNombre("Gabriel");
    propietario.setDireccion(new String());
    propietario.setCorreo("gabriel2022@gmail.com");
    propietario.setId(11L);
  }

  @Test
  void findAll(){
    /*Retorna lista de objetos tipo Propietario */
    when(propietarioRepository.findAll()).thenReturn(Arrays.asList(propietario));
    assertNotNull(propietarioServiceImpl.findAll());   
  }

   @Test
    void testFindOneList() {
       when(propietarioRepository.findAll()).thenReturn(Arrays.asList(propietario));
       Assertions.assertNotEquals(propietarioServiceImpl.findOne(11L), propietario);

    }
    

    @Test
    void testFindAllPage() {
      when(propietarioRepository.findAll()).thenReturn(Arrays.asList(
        new Propietario(11L,"Gabriel","Av.Peru",985678555L,"Tarjeta","gabriel2022@gmail.com")
      ));

      @SuppressWarnings("unchecked")
      Page<Propietario> propietarios = Mockito.mock(Page.class);
      Mockito.when(this.propietarioRepository.findAll((org.springframework.data.domain.Pageable) ArgumentMatchers.isA(Pageable.class))).thenReturn(propietarios);
      assertNotNull(propietarioServiceImpl.findAll());
    }
    
    @Test
    void testSave() {
      when(propietarioRepository.save(any(Propietario.class))).thenReturn(propietario);

      Propietario savePropietario = propietarioRepository.save(propietario);
      assertNotNull(savePropietario.getId());
    }

    @Test
    void testDelete() {
      final Long propietarioId =11L;
  
      propietarioServiceImpl.delete(propietarioId);
      propietarioServiceImpl.delete(propietarioId);

      verify(propietarioRepository,times(2)).deleteById(propietarioId);

    }
}