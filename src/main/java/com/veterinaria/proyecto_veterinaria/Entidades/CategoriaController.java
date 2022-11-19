package com.veterinaria.proyecto_veterinaria.Entidades;

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
public class CategoriaController {
    @Autowired
    private CategoriaService categoriaService;

    @GetMapping("/GestionCategoria")
    public String listarCategorias(@RequestParam(name = "page",defaultValue = "0")int page, Model model){
        Pageable pageRequest = PageRequest.of(page,7);
        Page<Categoria> categoria = categoriaService.findAll(pageRequest);
        PageRender<Categoria> pageRender = new PageRender<>("/GestionCategoria",categoria);
        model.addAttribute("categoria",categoria);
        model.addAttribute("page",pageRender);
        return "GestionCategoria";
    }
    
    @GetMapping("/formularioCategoria")
    public String RegistrarCategorias(Map<String,Object> modelo){
        Categoria categoria = new Categoria();
        modelo.put("categoria", categoria);
        modelo.put("titulo","Registrar Categoria");
        return "formularioCategoria";
    }

    @PostMapping("/formularioCategoria")
    public String guardarCategorias(@Valid Categoria categoria,BindingResult result,Model modelo, RedirectAttributes flash, SessionStatus status){
        if(result.hasErrors()){
            modelo.addAttribute("titulo", "Registrar Categoria");
            return "formularioCategoria";
        }
        String mensaje = (categoria.getId() != null) ? "La categoria " + categoria.getNombre() + " ha sido editado con exito" : "El categoria "+ categoria.getNombre() + " ha sido registrado con exito";
        categoriaService.save(categoria);
        status.setComplete();
        flash.addFlashAttribute("success",mensaje);
        return "redirect:/GestionCategoria";
    }

    @GetMapping("/formularioCategoria/{id}")
    public String editarCategorias(@PathVariable(value = "id") Long id, Map<String,Object> modelo, RedirectAttributes flash){
        Categoria categoria = null;
        if(id > 0){
            categoria = categoriaService.findOne(id);
            if(categoria == null){
                flash.addFlashAttribute("error","El ID de la Categoria no existe en la base de datos");
                return "redirect:/GestionCategoria";
            }
        }else{
            flash.addFlashAttribute("error","El ID de la Categoria no puede ser cero");
            return "redirect:/GestionCategoria";
        }
        
        modelo.put("categoria",categoria);
        modelo.put("titulo","Modificar Categoria");
        return "formularioCategoria";
    }

    @GetMapping("/eliminarCategoria/{id}")
    public String eliminarCategorias(@PathVariable(value = "id") Long id, RedirectAttributes flash){
        if(id > 0){
            categoriaService.delete(id);
            flash.addFlashAttribute("success","Categoria eliminado con exito");
        }
        return "redirect:/GestionCategoria";
    }
}
