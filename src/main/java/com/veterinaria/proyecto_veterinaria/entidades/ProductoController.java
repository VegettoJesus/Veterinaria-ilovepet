package com.veterinaria.proyecto_veterinaria.entidades;



import java.util.List;
import java.util.Map;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.veterinaria.proyecto_veterinaria.paginacion.PageRender;

@Controller
public class ProductoController {
    @Autowired
    private ProductoService productoService;

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping("/menu-producto")
    public String vistaMenuProducto(){
        return "menu-producto";
    }
    @GetMapping("/catalogoProducto")
    public String vistaCatalogo(@RequestParam(name = "page",defaultValue = "0")int page, Model model){
        Pageable pageRequest = PageRequest.of(page,10);
        Page<Producto> producto = productoService.findAll(pageRequest);
        PageRender<Producto> pageRender = new PageRender<>("/catalogoProducto",producto);
        model.addAttribute("producto",producto);
        model.addAttribute("page",pageRender);
        return "catalogoProducto";
    }

    @GetMapping("/detalleProducto/{id}")
    public String verDetallesDelProducto(@PathVariable(value = "id") Long id, Map<String, Object> modelo,RedirectAttributes flash){
        Producto producto = productoService.findOne(id);
        if(producto == null){
            flash.addFlashAttribute("error","El empleado no existe en la base de datos");
            return "redirect:/gestionProducto";
        }
        List<Categoria> listaCategoria = categoriaService.findAll();
        modelo.put("listaCategorias",listaCategoria);
        modelo.put("producto", producto);
        modelo.put("titulo",producto.getMarca()+ " - " + producto.getNombre());
        modelo.put("stockDispo","Está disponible en stock: "+producto.getStock() + " articulos");
        modelo.put("vence", "Este producto vence en: "+producto.getFechaVencimiento());
        modelo.put("precio","S/."+producto.getPrecio());
        return "detalleProducto";
    }

    @GetMapping("/gestionProducto")
    public String listarProducto(@RequestParam(name = "page",defaultValue = "0")int page, Model model){
        Pageable pageRequest = PageRequest.of(page,7);
        Page<Producto> producto = productoService.findAll(pageRequest);
        PageRender<Producto> pageRender = new PageRender<>("/gestionProducto",producto);
        model.addAttribute("producto",producto);
        model.addAttribute("page",pageRender);
        return "gestionProducto";
    }

    @GetMapping("/formularioProducto")
    public String registrarProducto(Map<String,Object> modelo){
        Producto producto = new Producto();
        modelo.put("listaCategorias",categoriaService.findAll());
        modelo.put("producto", producto);
        modelo.put("titulo","Registrar Producto");
        return "formularioProducto";
    }

    @PostMapping("/formularioProducto")
    public String guardarProducto(@Valid Producto producto,BindingResult result,Model modelo, RedirectAttributes flash, SessionStatus status){  
        if(result.hasErrors()){
            modelo.addAttribute("titulo", "Registrar Producto");
            return "formularioProducto";
        }
        String mensaje = (producto.getId() != null) ? "El producto ha sido editado con exito" : "El producto ha sido registrado con exito";
        productoService.save(producto);
        status.setComplete();
        flash.addFlashAttribute("success",mensaje);
        return "redirect:/gestionProducto";
    }

    @GetMapping("/formularioProducto/{id}")
    public String editarProducto(@PathVariable(value = "id") Long id, Map<String,Object> modelo, RedirectAttributes flash){
        Producto producto = null;
        if(id > 0){
            producto = productoService.findOne(id);
            if(producto == null){
                flash.addFlashAttribute("error","El ID del producto no existe en la base de datos");
                return "redirect:/gestionProducto";
            }
        }else{
            flash.addFlashAttribute("error","El ID del producto no puede ser cero");
            return "redirect:/gestionProducto";
        }
        modelo.put("listaCategorias",categoriaService.findAll());
        modelo.put("producto",producto);
        modelo.put("titulo","Modificar Producto");
        return "formularioProducto";
    }
    @GetMapping("/eliminarProducto/{id}")
    public String eliminarProducto(@PathVariable(value = "id") Long id, RedirectAttributes flash){
        if(id > 0){
            productoService.delete(id);
            flash.addFlashAttribute("success","Producto eliminado con exito");
        }
        return "redirect:/gestionProducto";
    }
}
