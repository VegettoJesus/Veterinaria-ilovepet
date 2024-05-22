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
import com.veterinaria.proyecto_veterinaria.entidadUsuario.EmpleadoService;
import com.veterinaria.proyecto_veterinaria.paginacion.PageRender;

@Controller
public class CitasController {

    @Autowired
    private CitasService citasService;

    @Autowired
    private EmpleadoService empleadoService;

    @Autowired
    private MascotaService mascotaService;

    @Autowired
    private ServicioService servicioService;

    @Autowired
    private PropietarioService propietarioService;

    @GetMapping("gestionAtencion")
    public String listarAtenciones(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {
        Pageable pageRequest = PageRequest.of(page, 7);
        Page<Citas> citas = citasService.findAll(pageRequest);
        PageRender<Citas> pageRender = new PageRender<>("/gestionAtencion", citas);
        model.addAttribute("citas", citas);
        model.addAttribute("page", pageRender);
        return "gestionAtencion";
    }

    @GetMapping("/formularioAtencion/{id}")
    public String editarAtenciones(@PathVariable(value = "id") Long id, Map<String, Object> modelo,
            RedirectAttributes flash) {
        Citas citas = new Citas();
        if (id > 0) {
            citas = citasService.findOne(id);
            if (citas == null) {
                flash.addFlashAttribute("error", "El ID de la atencion no existe en la base de datos");
                return "redirect:/gestionAtencion";
            }
        } else {
            flash.addFlashAttribute("error", "El ID de la atencion no puede ser cero");
            return "redirect:/gestionAtencion";
        }
        modelo.put("empleado", empleadoService.findAll());
        modelo.put("mascota", mascotaService.findAll());
        modelo.put("servicio", servicioService.findAll());
        modelo.put("propietario", propietarioService.findAll());
        modelo.put("citas", citas);
        modelo.put("titulo", "Modificar Atención");
        return "formularioAtencion";
    }

    @PostMapping("/formularioAtencion")
    public String guardarAtenciones(@Valid Citas citas, BindingResult result, Model modelo, RedirectAttributes flash,
            SessionStatus status) {
        String mensaje = (citas.getId() != null) ? "La Atención ha sido editado con exito"
                : "La Atención ha sido registrado con exito";

        citasService.save(citas);
        status.setComplete();
        flash.addFlashAttribute("success", mensaje);
        return "redirect:/gestionAtencion";
    }

    @GetMapping("gestionCitas")
    public String listarCitas(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {
        Pageable pageRequest = PageRequest.of(page, 7);
        Page<Citas> citas = citasService.findAll(pageRequest);

        PageRender<Citas> pageRender = new PageRender<>("/gestionCitas", citas);
        model.addAttribute("citas", citas);

        model.addAttribute("page", pageRender);
        return "gestionCitas";
    }

    @GetMapping("/formularioCitas")
    public String registrarCitas(Map<String, Object> modelo) {
        Citas citas = new Citas();
        modelo.put("empleado", empleadoService.findAll());
        modelo.put("propietario", propietarioService.findAll());
        modelo.put("citas", citas);
        modelo.put("titulo", "Registrar Cita");
        return "formularioCitas";
    }

    @PostMapping("/formularioCitas")
    public String guardarCitas(@Valid Citas citas, BindingResult result, Model modelo,
            RedirectAttributes flash, SessionStatus status) {
        if (result.hasErrors()) {
            modelo.addAttribute("titulo", "Registrar Cita");
            return "formularioCitas";
        }

        // Validar si ya existe una cita con la misma fecha, hora de cita y servicio
        if (citasService.existsByFechaCitaAndHoraCitaAndServicio(citas.getFechaCita(), citas.getHoraCita(),
                citas.getServicio())) {
            flash.addFlashAttribute("error",
                    "Ya existe una cita registrada con la misma fecha, hora de cita y servicio.");
            return "redirect:/formularioCitas";
        }

        String mensaje = (citas.getId() != null) ? "La cita ha sido editado con exito"
                : "La cita ha sido registrado con exito";
        citasService.save(citas);
        status.setComplete();
        flash.addFlashAttribute("success", mensaje);
        return "redirect:/gestionCitas";
    }

    @GetMapping("/formularioCitas/obtenerMascota/{propietarioId}")
    public String cargarMascotas(@PathVariable Long propietarioId, Model model) {
        Propietario propietario = propietarioService.findOne(propietarioId);
        if (propietario != null) {
            List<Mascota> mascotas = propietario.getMascotas();
            model.addAttribute("mascotas", mascotas); // Agregar las mascotas al modelo
        }
        return "opciones-mascotas"; // Devolver el nombre del fragmento HTML que contiene las opciones de mascotas
    }

    @GetMapping("/formularioCitas/obtenerTipoMascota/{tipo_mascota}")
    public String cargarTipoMascotas(@PathVariable Long tipo_mascota, Model model) {
        if(tipo_mascota !=null){
            List<Servicio> servicio = servicioService.findByTipoMascota(tipo_mascota);
            model.addAttribute("servicio", servicio);
        }
        
        return "opciones-servicios"; // Devolver el nombre del fragmento HTML que contiene las opciones de servicios
    }

    @GetMapping("/formularioCitas/{id}")
    public String editarCitas(@PathVariable(value = "id") Long id, Map<String, Object> modelo,
            RedirectAttributes flash) {
        Citas citas = null;
        if (id > 0) {
            citas = citasService.findOne(id);
            if (citas == null) {
                flash.addFlashAttribute("error", "El ID de la cita no existe en la base de datos");
                return "redirect:/gestionCitas";
            }
        } else {
            flash.addFlashAttribute("error", "El ID de la cita no puede ser cero");
            return "redirect:/gestionCitas";
        }
        modelo.put("empleado", empleadoService.findAll());
        modelo.put("mascota", mascotaService.findAll());
        modelo.put("servicio", servicioService.findAll());
        modelo.put("propietario", propietarioService.findAll());
        modelo.put("citas", citas);
        modelo.put("titulo", "Modificar Cita");
        return "formularioCitas";
    }

    @GetMapping("/eliminarCitas/{id}")
    public String eliminarCitas(@PathVariable(value = "id") Long id, RedirectAttributes flash) {
        if (id > 0) {
            citasService.delete(id);
            flash.addFlashAttribute("success", "Cita eliminado con exito");
        }
        return "redirect:/gestionCitas";
    }
}
