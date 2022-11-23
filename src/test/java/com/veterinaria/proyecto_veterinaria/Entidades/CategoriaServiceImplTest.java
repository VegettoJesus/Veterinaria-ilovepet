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

import com.veterinaria.proyecto_veterinaria.entidades.Categoria;
import com.veterinaria.proyecto_veterinaria.entidades.CategoriaRepository;
import com.veterinaria.proyecto_veterinaria.entidades.CategoriaServiceImpl;

class CategoriaServiceImplTest {
    @Mock
    private CategoriaRepository categoriaRepository;

   @InjectMocks
   private CategoriaServiceImpl categoriaServiceImpl;
 
  private Categoria categoria;

  @BeforeEach
  void setUp(){
    MockitoAnnotations.openMocks(this);

    categoria = new Categoria();
    categoria.setId(350L);
    categoria.setNombre("Cualquiera");
  }
    @Test
    void testDelete() {
    final Long categoriaId =350L;

    categoriaServiceImpl.delete(categoriaId);
    categoriaServiceImpl.delete(categoriaId);
    categoriaServiceImpl.delete(categoriaId);

    verify(categoriaRepository,times(3)).deleteById(categoriaId);
    }

    @Test
    void testFindAll() {
        when(categoriaRepository.findAll()).thenReturn(Arrays.asList(categoria));
        assertNotNull(categoriaServiceImpl.findAll());
    }

    @Test
    void testFindAllByPage() {
    when(categoriaRepository.findAll()).thenReturn(Arrays.asList(
    new Categoria(350L,"Cualquiera")
    ));

      @SuppressWarnings("unchecked")
      Page<Categoria> categorias = Mockito.mock(Page.class);
      Mockito.when(this.categoriaRepository.findAll((org.springframework.data.domain.Pageable) ArgumentMatchers.isA(Pageable.class))).thenReturn(categorias);
      assertNotNull(categoriaServiceImpl.findAll());
    }

    @Test
    void testFindOne() {
        when(categoriaRepository.findAll()).thenReturn(Arrays.asList(categoria));
        Assertions.assertNotEquals(categoriaServiceImpl.findOne(350L), categoria);
    }

    @Test
    void testSave() {
    when(categoriaRepository.save(any(Categoria.class))).thenReturn(categoria);

      Categoria saveCategoria = categoriaRepository.save(categoria);
      assertNotNull(saveCategoria.getId());
    }
}
