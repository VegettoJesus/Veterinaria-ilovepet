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
public class RolController {
    @Autowired
    private RolService rolService;

    @GetMapping("/GestionRol")
    public String listarRoles(@RequestParam(name = "page",defaultValue = "0")int page, Model model){
        Pageable pageRequest = PageRequest.of(page,7);
        Page<Rol> rol = rolService.findAll(pageRequest);
        PageRender<Rol> pageRender = new PageRender<>("/GestionRol",rol);
        model.addAttribute("rol",rol);
        model.addAttribute("page",pageRender);
        return "GestionRol";
    }
    
    @GetMapping("/formularioRol")
    public String RegistrarRoles(Map<String,Object> modelo){
        Rol rol = new Rol();
        modelo.put("rol", rol);
        modelo.put("titulo","Registrar Rol");
        return "formularioRol";
    }

    @PostMapping("/formularioRol")
    public String guardarRoles(@Valid Rol rol,BindingResult result,Model modelo, RedirectAttributes flash, SessionStatus status){
        if(result.hasErrors()){
            modelo.addAttribute("titulo", "Registrar Rol");
            return "formularioRol";
        }
        String mensaje = (rol.getId() != null) ? "El Rol " + rol.getNombre() + " ha sido editado con exito" : "El Rol "+ rol.getNombre() + " ha sido registrado con exito";
        rolService.save(rol);
        status.setComplete();
        flash.addFlashAttribute("success",mensaje);
        return "redirect:/GestionRol";
    }

    @GetMapping("/formularioRol/{id}")
    public String editarRoles(@PathVariable(value = "id") Long id, Map<String,Object> modelo, RedirectAttributes flash){
        Rol rol = null;
        if(id > 0){
            rol = rolService.findOne(id);
            if(rol == null){
                flash.addFlashAttribute("error","El ID del rol no existe en la base de datos");
                return "redirect:/GestionRol";
            }
        }else{
            flash.addFlashAttribute("error","El ID del rol no puede ser cero");
            return "redirect:/GestionRol";
        }
        
        modelo.put("rol",rol);
        modelo.put("titulo","Modificar Rol");
        return "formularioRol";
    }

    @GetMapping("/eliminarRol/{id}")
    public String eliminarRoles(@PathVariable(value = "id") Long id, RedirectAttributes flash){
        if(id > 0){
            rolService.delete(id);
            flash.addFlashAttribute("success","Rol eliminado con exito");
        }
        return "redirect:/GestionRol";
    }
}
