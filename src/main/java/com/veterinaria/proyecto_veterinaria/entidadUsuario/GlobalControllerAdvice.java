package com.veterinaria.proyecto_veterinaria.entidadUsuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalControllerAdvice {
    @Autowired
    private EmpleadoService empleadoService;
    
    @ModelAttribute
    public void addUsernameToModel(Model model) {
         Authentication auth = SecurityContextHolder.getContext().getAuthentication();
         String email = auth.getName();
 
         empleadoLogin empleado = empleadoService.findByEmail(email);
 
         if (empleado != null) {
             model.addAttribute("nombreEmpleado", empleado.getNombre());
             model.addAttribute("apellidoEmpleado", empleado.getApellido());
             model.addAttribute("idEmpleado", empleado.getId());
                if (!empleado.getTipoRol().isEmpty()) {
                    model.addAttribute("rolEmpleado", empleado.getTipoRol().get(0).getNombre());
                }
         }
    }
}
