package com.veterinaria.proyecto_veterinaria.entidades;

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
public class PropietarioController {

    @Autowired
    private PropietarioService propietarioService;

    @GetMapping("/gestionPropietario")
    public String listarPropietarios(@RequestParam(name = "page",defaultValue = "0")int page, Model model){
        Pageable pageRequest = PageRequest.of(page,7);
        Page<Propietario> propietario = propietarioService.findAll(pageRequest);
        PageRender<Propietario> pageRender = new PageRender<>("/gestionPropietario",propietario);
        model.addAttribute("propietario",propietario);
        model.addAttribute("page",pageRender);
        return "gestionPropietario";
    }

    @GetMapping("/formularioPropietario")
    public String registrarPropietarios(Map<String,Object> modelo){
        Propietario propietario = new Propietario();
        modelo.put("propietario", propietario);
        modelo.put("titulo","Registrar Propietario");
        return "formularioPropietario";
    }

    @PostMapping("/formularioPropietario")
    public String guardarPropietarios(@Valid Propietario propietario,BindingResult result,Model modelo, RedirectAttributes flash, SessionStatus status){
        if(result.hasErrors()){
            modelo.addAttribute("titulo", "Registrar Propietario");
            return "formularioPropietario";
        }
        String mensaje = (propietario.getId() != null) ? "El propietario " + propietario.getNombre() + " ha sido editado con exito" : "El propietario "+ propietario.getNombre() + " ha sido registrado con exito";
        propietarioService.save(propietario);
        status.setComplete();
        flash.addFlashAttribute("success",mensaje);
        return "redirect:/gestionPropietario";
    }

    @GetMapping("/formularioPropietario/{id}")
    public String editarPropietarios(@PathVariable(value = "id") Long id, Map<String,Object> modelo, RedirectAttributes flash){
        Propietario propietario = null;
        if(id > 0){
            propietario = propietarioService.findOne(id);
            if(propietario == null){
                flash.addFlashAttribute("error","El ID del Propietario no existe en la base de datos");
                return "redirect:/gestionPropietario";
            }
        }else{
            flash.addFlashAttribute("error","El ID del Propietario no puede ser cero");
            return "redirect:/gestionPropietario";
        }
        
        modelo.put("propietario",propietario);
        modelo.put("titulo","Modificar Propietario");
        return "formularioPropietario";
    }

    @GetMapping("/eliminarPropietario/{id}")
    public String eliminarPropietario(@PathVariable(value = "id") Long id, RedirectAttributes flash){
        if(id > 0){
            propietarioService.delete(id);
            flash.addFlashAttribute("success","Propietario eliminado con exito");
        }
        return "redirect:/gestionPropietario";
    }
}
