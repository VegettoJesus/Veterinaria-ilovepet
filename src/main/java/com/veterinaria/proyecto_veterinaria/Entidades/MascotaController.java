package com.veterinaria.proyecto_veterinaria.entidades;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
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

import com.lowagie.text.DocumentException;
import com.veterinaria.proyecto_veterinaria.paginacion.PageRender;

@Controller
public class MascotaController {

    @Autowired
    private MascotaService mascotaService;

    @Autowired
    private PropietarioService propietarioService;

    @Autowired
    private CitasService citasService;

    @GetMapping("/detalleMascota/{id}")
    public String verDetallesMascota(@PathVariable(value = "id") Long id, Map<String, Object> modelo,RedirectAttributes flash){
        Mascota mascota = mascotaService.findOne(id);
        if(mascota == null){
            flash.addFlashAttribute("error","La mascota no existe en la base de datos");
            return "redirect:/gestionMascota";
        }
        List<Propietario> listaPropietario = propietarioService.findAll();
        modelo.put("listaPropietario",listaPropietario);
        modelo.put("mascota", mascota);
        modelo.put("titulo",mascota.getNombre());
        return "detalleMascota";
    }

    @GetMapping("/historialMedico/{id}")
    public String verHistorialMedico(@PathVariable(value = "id") Long id,@RequestParam(name = "page",defaultValue = "0")int page,Model model, Map<String, Object> modelo,RedirectAttributes flash){
        Mascota mascota = mascotaService.findOne(id);
        if(mascota == null){
            flash.addFlashAttribute("error","La mascota no existe en la base de datos");
            return "redirect:/gestionMascota";
        }
        List<Propietario> listaPropietario = propietarioService.findAll();
        modelo.put("listaPropietario",listaPropietario);
        modelo.put("listaCitas",citasService.ordenarDesc());
        modelo.put("mascota", mascota);
        modelo.put("titulo",mascota.getNombre());
        return "historialMedico";
    }

    @GetMapping("/gestionMascota")
    public String listarMascota(@RequestParam(name = "page",defaultValue = "0")int page, Model model){
        Pageable pageRequest = PageRequest.of(page,7);
        Page<Mascota> mascota = mascotaService.findAll(pageRequest);
        PageRender<Mascota> pageRender = new PageRender<>("/gestionMascota",mascota);
        model.addAttribute("mascota",mascota);
        model.addAttribute("page",pageRender);
        return "gestionMascota";
    }

    @GetMapping("/formularioMascota")
    public String registrarMascota(Map<String,Object> modelo){
        Mascota mascota = new Mascota();
        modelo.put("listaPropietario",propietarioService.findAll());
        modelo.put("mascota", mascota);
        modelo.put("titulo","Registrar Mascota");
        return "formularioMascota";
    }

    @PostMapping("/formularioMascota")
    public String guardarMascota(@Valid Mascota mascota,BindingResult result,Model modelo, RedirectAttributes flash, SessionStatus status){  
        if(result.hasErrors()){
            modelo.addAttribute("titulo", "Registrar Mascota");
            return "formularioMascota";
        }
        String mensaje = (mascota.getId() != null) ? "La mascota ha sido editado con exito" : "La mascota ha sido registrado con exito";
        mascotaService.save(mascota);
        status.setComplete();
        flash.addFlashAttribute("success",mensaje);
        return "redirect:/gestionMascota";
    }
    @GetMapping("/formularioMascota/{id}")
    public String editarMascota(@PathVariable(value = "id") Long id, Map<String,Object> modelo, RedirectAttributes flash){
        Mascota mascota = null;
        if(id > 0){
            mascota = mascotaService.findOne(id);
            if(mascota == null){
                flash.addFlashAttribute("error","El ID de la mascota no existe en la base de datos");
                return "redirect:/gestionMascota";
            }
        }else{
            flash.addFlashAttribute("error","El ID de la mascota no puede ser cero");
            return "redirect:/gestionMascota";
        }
        modelo.put("listaPropietario",propietarioService.findAll());
        modelo.put("mascota",mascota);
        modelo.put("titulo","Modificar Mascota");
        return "formularioMascota";
    }

    @GetMapping("/eliminarMascota/{id}")
    public String eliminarMascota(@PathVariable(value = "id") Long id, RedirectAttributes flash){
        if(id > 0){
            mascotaService.delete(id);
            flash.addFlashAttribute("success","Mascota eliminado con exito");
        }
        return "redirect:/gestionMascota";
    }

    @GetMapping("/exportarHistorialPDF/{id}")
    public void exportarListadoDeEmpleadosPDF(@PathVariable(value = "id") Long id, HttpServletResponse response) throws DocumentException, IOException{
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String fechaActual = dateFormatter.format(new Date());

        String cabecera = "Content-Disposition";
        String valor = "attachment; filename=Mascota_"+fechaActual+".pdf";

        response.setHeader(cabecera, valor);

        List<Citas> citas = citasService.listaAtendidos();
        HistorialExporterPDF exporter = new HistorialExporterPDF(citas,id);
        exporter.exportar(response);
    }
    
}
