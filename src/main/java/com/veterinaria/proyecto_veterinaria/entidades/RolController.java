package com.veterinaria.proyecto_veterinaria.entidades;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RolController {
    @Autowired
    private RolService rolService;
    
    @GetMapping("/gestionAdmin/roles")
    @ResponseBody
    public Map<String, Object> listarRoles() {
        List<Rol> roles = rolService.findAll(); 
        
        Map<String, Object> response = new HashMap<>();
        response.put("data", roles); 
        
        return response;
    }
        
    @PostMapping("/guardarRol")
    @ResponseBody
    public Map<String, Object> guardarRol(@RequestParam("nombre") String nombre, @RequestParam("id") Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            Rol rol;
            if (id > 0) {

                rol = rolService.findOne(id);
                if (rol != null) {
                    rol.setNombre(nombre); 
                    rolService.save(rol);  
                } else {
                    throw new Exception("Rol no encontrado");
                }
            } else {

                rol = new Rol(nombre);
                rolService.save(rol);
            }

            response.put("status", "success");
            response.put("message", id > 0 ? "El rol ha sido actualizado correctamente" : "El rol ha sido registrado correctamente");
            response.put("data", Arrays.asList(rol));
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Hubo un problema al guardar el rol: " + e.getMessage());
        }
        return response;
    }

    @GetMapping("/eliminarRol/{id}")
    public @ResponseBody Map<String, Object> eliminarRoles(@PathVariable(value = "id") Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            if (id > 0) {
                rolService.delete(id);  
                response.put("status", "success");
                response.put("message", "Rol eliminado con éxito");
            } else {
                response.put("status", "error");
                response.put("message", "Rol no encontrado");
            }
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Hubo un problema al eliminar el rol");
        }
        return response;
    }

}
