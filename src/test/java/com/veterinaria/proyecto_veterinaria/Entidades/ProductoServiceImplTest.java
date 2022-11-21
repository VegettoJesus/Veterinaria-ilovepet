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

import com.veterinaria.proyecto_veterinaria.entidades.Producto;
import com.veterinaria.proyecto_veterinaria.entidades.ProductoRepository;
import com.veterinaria.proyecto_veterinaria.entidades.ProductoServiceImpl;

class ProductoServiceImplTest {
     /*Proviene del ProductoserviceImpl porque usa el repositorio */
  @Mock
  private ProductoRepository productoRepository;

   @InjectMocks
   private ProductoServiceImpl productoServiceImpl;
 
  private Producto producto;

  @BeforeEach
  void setUp(){

    /*MockitoAnnotations.initMocks(this); */
    MockitoAnnotations.openMocks(this);

    producto = new Producto();
    producto.setId(40L);
    producto.setNombre("Manuel");
    producto.setDescripcion("Ricocan");
    producto.setPrecio(200);
  }
    @Test
    void testDelete() {

      final Long productoId =1L;

      productoServiceImpl.delete(productoId);

      verify(productoRepository,times(1)).deleteById(productoId);

    }

    @Test
    void testFindAll() {
        when(productoRepository.findAll()).thenReturn(Arrays.asList(producto));
        assertNotNull(productoServiceImpl.findAll());

    }

    @Test
    void testFindAll2() {
      when(productoRepository.findAll()).thenReturn(Arrays.asList(
        new Producto(40L,"Manuel","Cepillo",200,50L),
        new Producto(20L,"Pepe","Hueso",100,20L)
      ));

      @SuppressWarnings("unchecked")
      Page<Producto> productos = Mockito.mock(Page.class);
      Mockito.when(this.productoRepository.findAll((org.springframework.data.domain.Pageable) ArgumentMatchers.isA(Pageable.class))).thenReturn(productos);
      

    }

    @Test
    void testFindOne() {
        when(productoRepository.findAll()).thenReturn(Arrays.asList(producto));
        Assertions.assertNotEquals(productoServiceImpl.findOne(40L), producto);
    }

    @Test
    void testSave() {

      when(productoRepository.save(any(Producto.class))).thenReturn(producto);

      Producto saveProducto = productoRepository.save(producto);
      assertNotNull(saveProducto.getId());


    }
}