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
public class ServicioController {

    @Autowired
    private ServicioService servicioService;

    @GetMapping("/menu-servicio")
    public String vistaMenu(){
        return "menu-servicio";
    }

    @GetMapping("/catalogoServicios")
    public String vistaCatalogo(@RequestParam(name = "page",defaultValue = "0")int page, Model model){
        Pageable pageRequest = PageRequest.of(page,10);
        Page<Servicio> servicio = servicioService.findAll(pageRequest);
        PageRender<Servicio> pageRender = new PageRender<>("/catalogoServicios",servicio);
        model.addAttribute("servicio",servicio);
        model.addAttribute("page",pageRender);
        return "catalogoServicios";
    }

    @GetMapping("/gestionServicio")
    public String listarServicios(@RequestParam(name = "page",defaultValue = "0")int page, Model model){
        Pageable pageRequest = PageRequest.of(page,7);
        Page<Servicio> servicio = servicioService.findAll(pageRequest);
        PageRender<Servicio> pageRender = new PageRender<>("/gestionServicio",servicio);
        model.addAttribute("servicio",servicio);
        model.addAttribute("page",pageRender);
        return "gestionServicio";
    }

    @GetMapping("/formularioServicio")
    public String registrarServicio(Map<String,Object> modelo){
        Servicio servicio = new Servicio();
        modelo.put("servicio", servicio);
        modelo.put("titulo","Registrar Servicio");
        return "formularioServicio";
    }
    @PostMapping("/formularioServicio")
    public String guardarServicio(@Valid Servicio servicio,BindingResult result,Model modelo, RedirectAttributes flash, SessionStatus status){
        if(result.hasErrors()){
            modelo.addAttribute("titulo", "Registrar Servicio");
            return "formularioServicio";
        }
        String mensaje = (servicio.getId() != null) ? "El servicio " + servicio.getDescripcion() + " ha sido editado con exito" : "El servicio "+ servicio.getDescripcion() + " ha sido registrado con exito";
        servicioService.save(servicio);
        status.setComplete();
        flash.addFlashAttribute("success",mensaje);
        return "redirect:/gestionServicio";
    }

    @GetMapping("/formularioServicio/{id}")
    public String editarServicio(@PathVariable(value = "id") Long id, Map<String,Object> modelo, RedirectAttributes flash){
        Servicio servicio = null;
        if(id > 0){
            servicio = servicioService.findOne(id);
            if(servicio == null){
                flash.addFlashAttribute("error","El ID del servicio no existe en la base de datos");
                return "redirect:/gestionServicio";
            }
        }else{
            flash.addFlashAttribute("error","El ID del servicio no puede ser cero");
            return "redirect:/gestionServicio";
        }
        
        modelo.put("servicio",servicio);
        modelo.put("titulo","Modificar Servicio");
        return "formularioServicio";
    }

    @GetMapping("/eliminarServicio/{id}")
    public String eliminarServicio(@PathVariable(value = "id") Long id, RedirectAttributes flash){
        if(id > 0){
            servicioService.delete(id);
            flash.addFlashAttribute("success","Servicio eliminado con exito");
        }
        return "redirect:/gestionServicio";
    }

}
