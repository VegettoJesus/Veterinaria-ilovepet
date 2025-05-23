package com.veterinaria.proyecto_veterinaria.entidadUsuario;

import java.io.IOException;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.nio.file.Files;
import java.nio.file.Path;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.lowagie.text.DocumentException;
import com.veterinaria.proyecto_veterinaria.entidades.HorarioEmpleado;
import com.veterinaria.proyecto_veterinaria.entidades.HorarioEmpleadoService;
import com.veterinaria.proyecto_veterinaria.entidades.RegistroAsistenciaService;
import com.veterinaria.proyecto_veterinaria.entidades.RolService;
import com.veterinaria.proyecto_veterinaria.paginacion.PageRender;


@Controller
public class EmpleadoController {
     
    @Autowired
    private EmpleadoService empleadoService;

    @Autowired
    private HorarioEmpleadoService horarioEmpleadoService;

    @Autowired
    private RegistroAsistenciaService registroAsistenciaService;

    @Autowired
    private RolService rolservice;

    @GetMapping({"/","/login",""})
    public String iniciarSesion(){
        return "login";
    }

    @GetMapping("/detalleEmpleado/modal/{id}")
    @ResponseBody
    public ResponseEntity<?> obtenerDetalleEmpleado(
        @PathVariable Long id,
        @RequestParam(value = "anio", required = false) Integer anio,
        @RequestParam(value = "mes", required = false) Integer mes) {
    
        // Si el año o mes son nulos, se asigna el valor del año y mes actuales
        if (anio == null) {
            anio = LocalDate.now().getYear();  // Año actual
        }
        if (mes == null) {
            mes = LocalDate.now().getMonthValue();  // Mes actual
        }
    
        empleadoLogin empleado = empleadoService.findOne(id);
        if (empleado == null) {
            return ResponseEntity.notFound().build();
        }
    
        Map<String, Object> response = new HashMap<>();
        response.put("empleado", empleado);
        response.put("resumenAsistencia", registroAsistenciaService.obtenerResumenMensualPorEmpleado(id, anio, mes)); // Método actualizado en el servicio
        response.put("horarios", horarioEmpleadoService.findByEmpleadoId(id));
    
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/gestionAdmin")
    public String listarEmpleados(
            @RequestParam(name = "buscar", required = false) String buscar,
            @RequestParam(name = "page", defaultValue = "0") int page,
            Model model) {

        Pageable pageRequest = PageRequest.of(page, 7);
        Page<empleadoLogin> empleados;

        if (buscar != null && !buscar.trim().isEmpty()) {
            empleados = empleadoService.buscarPorFiltro(buscar, pageRequest);
        } else {
            empleados = empleadoService.findAllExcludingId1(pageRequest);
        }

        PageRender<empleadoLogin> pageRender = new PageRender<>("/gestionAdmin?buscar=" + (buscar != null ? buscar : ""), empleados);

        model.addAttribute("empleados", empleados);
        model.addAttribute("page", pageRender);
        model.addAttribute("buscar", buscar);

        return "gestionAdmin";
    }
 
    @GetMapping("/formularioEmpleado")
    public String registrarEmpleado(Map<String,Object> modelo){
        empleadoLogin empleado = new empleadoLogin();
        modelo.put("rol",rolservice.findAll());
        modelo.put("empleado", empleado);
        modelo.put("titulo","Registrar Empleado");
        return "formularioEmpleado";
    }

    @PostMapping("/formularioEmpleado")
    public String guardarEmpleado(
            @Valid empleadoLogin empleado,
            BindingResult result,
            Model modelo,
            @RequestParam("imagenFile") MultipartFile archivoImagen,
            RedirectAttributes flash,
            SessionStatus status) {

        if (result.hasErrors()) {
            modelo.addAttribute("titulo", "Registrar Empleado");
            return "formularioEmpleado";
        }

        // Verifica si estamos actualizando (tiene ID) y tiene imagen anterior
        empleadoLogin empleadoExistente = (empleado.getId() != null) ? empleadoService.findOne(empleado.getId()) : null;

        if (!archivoImagen.isEmpty()) {
            String nombreArchivo = UUID.randomUUID().toString() + "_" + archivoImagen.getOriginalFilename();
            Path rutaCarpeta = Paths.get("src/main/resources/static/imagenPerfil").toAbsolutePath();
            Path rutaCompleta = rutaCarpeta.resolve(nombreArchivo);

            try {
                Files.createDirectories(rutaCarpeta);
                archivoImagen.transferTo(rutaCompleta.toFile());

                // Si existe una imagen anterior, la eliminamos
                if (empleadoExistente != null && empleadoExistente.getImagen() != null && !empleadoExistente.getImagen().isEmpty()) {
                    Path rutaImagenAnterior = rutaCarpeta.resolve(Paths.get(empleadoExistente.getImagen()).getFileName());
                    Files.deleteIfExists(rutaImagenAnterior);
                }

                // Guardamos la nueva ruta en el objeto
                empleado.setImagen("/imagenPerfil/" + nombreArchivo);

            } catch (IOException e) {
                e.printStackTrace();
                flash.addFlashAttribute("error", "Error al subir la imagen.");
                return "redirect:/formularioEmpleado";
            }
        } else if (empleadoExistente != null) {
            // Si no se sube una nueva imagen, mantener la anterior
            empleado.setImagen(empleadoExistente.getImagen());
        }

        empleadoService.save(empleado);
        status.setComplete();

        String mensaje = (empleado.getId() != null)
                ? "El empleado ha sido editado con éxito"
                : "El empleado ha sido registrado con éxito";

        flash.addFlashAttribute("success", mensaje);

        return "redirect:/gestionAdmin";
    }

    @GetMapping("/formularioEmpleado/{id}")
    public String editarEmpleado(@PathVariable(value = "id") Long id, Map<String,Object> modelo, RedirectAttributes flash){
        empleadoLogin empleado = null;
        if(id > 0){
            empleado = empleadoService.findOne(id);
            if(empleado == null){
                flash.addFlashAttribute("error","El ID del empleado no existe en la base de datos");
                return "redirect:/gestionAdmin";
            }
        }else{
            flash.addFlashAttribute("error","El ID del empleado no puede ser cero");
            return "redirect:/gestionAdmin";
        }
        modelo.put("rol",rolservice.findAll());
        modelo.put("empleado",empleado);
        modelo.put("titulo","Modificar Empleado");
        return "formularioEmpleado";
    }
    
    @DeleteMapping("/gestionAdmin/eliminar/{id}")
    public ResponseEntity<Map<String, Object>> deleteEmpleado(@PathVariable("id") Long id) {
        Map<String, Object> response = new HashMap<>();

        try {
            // Obtener el empleado
            empleadoLogin empleado = empleadoService.findOne(id);

            if (empleado != null) {
                // Eliminar los registros de horario de empleado
                horarioEmpleadoService.deleteByEmpleado(empleado);

                // Eliminar los registros de asistencia
                registroAsistenciaService.deleteByEmpleado(empleado);

                // Eliminar la imagen si existe
                if (empleado.getImagen() != null && !empleado.getImagen().isEmpty()) {
                    Path rutaCarpeta = Paths.get("src/main/resources/static").toAbsolutePath();
                    Path rutaImagen = rutaCarpeta.resolve(empleado.getImagen().substring(1)); 
                    try {
                        Files.deleteIfExists(rutaImagen);
                    } catch (IOException e) {
                        // Manejo de error si no se puede eliminar la imagen
                        e.printStackTrace();
                    }
                }

                // Eliminar el empleado
                empleadoService.delete(id);

                response.put("success", true);
                response.put("message", "Empleado eliminado exitosamente.");
                response.put("status", "success");
            } else {
                response.put("success", false);
                response.put("message", "Empleado no encontrado.");
                response.put("status", "error");
            }

        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Hubo un error al eliminar el empleado.");
            response.put("status", "error");
            response.put("error", e.getMessage());
            e.printStackTrace();
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping("/exportarPDF")
    public void exportarListadoDeEmpleadosPDF(HttpServletResponse response) throws DocumentException, IOException {
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String fechaActual = dateFormatter.format(new Date());

        String cabecera = "Content-Disposition";
        String valor = "attachment; filename=Empleados_"+fechaActual+".pdf";

        response.setHeader(cabecera, valor);

        List<empleadoLogin> empleados = empleadoService.findAll();
        EmpleadoExporterPDF exporter = new EmpleadoExporterPDF(empleados);
        exporter.exportar(response);
    }

    @PostMapping("/registrarHorario")
    public String registrarHorario(
            @RequestParam Long empleadoId,
            @RequestParam("diasSemana[]") List<String> diasSemana,
            @RequestParam("horaEntrada[]") List<LocalTime> horasEntrada,
            @RequestParam("horaSalida[]") List<LocalTime> horasSalida,
            @RequestParam("fechaInicio[]") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) List<LocalDate> fechasInicio,
            @RequestParam("fechaFin[]") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) List<LocalDate> fechasFin,
            @RequestParam(value = "descanso", required = false) List<String> diasDescanso,
            RedirectAttributes redirectAttributes
    ) {
        try {
            empleadoLogin empleado = empleadoService.findOne(empleadoId);

            for (int i = 0; i < diasSemana.size(); i++) {
                HorarioEmpleado horario = new HorarioEmpleado();
                horario.setEmpleado(empleado);
                horario.setDiaSemana(diasSemana.get(i));

                if (diasDescanso != null && diasDescanso.contains(diasSemana.get(i))) {
                    horario.setHoraEntrada(null);
                    horario.setHoraSalida(null);
                } else {
                    horario.setHoraEntrada(horasEntrada.get(i));
                    horario.setHoraSalida(horasSalida.get(i));
                }

                horario.setFechaInicio(fechasInicio.get(i));
                horario.setFechaFin(fechasFin.get(i));
                horarioEmpleadoService.save(horario);
            }

            redirectAttributes.addFlashAttribute("message", "¡Horario registrado correctamente!");
            redirectAttributes.addFlashAttribute("alertType", "success");

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Hubo un error al registrar el horario.");
            redirectAttributes.addFlashAttribute("alertType", "error");
        }

        return "redirect:/gestionAdmin";
    }

    @GetMapping("/horario/mes/{idEmpleado}")
    @ResponseBody
    public List<HorarioEmpleado> listarHorariosDelMes(@PathVariable Long idEmpleado) {
        YearMonth mesActual = YearMonth.now();
        LocalDate inicio = mesActual.atDay(1);
        LocalDate fin = mesActual.atEndOfMonth();
        
        return horarioEmpleadoService.findByEmpleadoIdAndFechaInicioBetween(idEmpleado, inicio, fin);
    }

    @PostMapping("/horario/actualizar")
    @ResponseBody
    public ResponseEntity<?> actualizarHorario(@RequestBody Map<String, String> data) {
        Long id = Long.parseLong(data.get("id"));
        HorarioEmpleado horario = horarioEmpleadoService.findOne(id);

        if (horario != null) {
            try {
                String entrada = data.get("horaEntrada");
                String salida = data.get("horaSalida");

                horario.setHoraEntrada((entrada == null || entrada.isEmpty()) ? null : LocalTime.parse(entrada));
                horario.setHoraSalida((salida == null || salida.isEmpty()) ? null : LocalTime.parse(salida));

                horarioEmpleadoService.save(horario);
                return ResponseEntity.ok().build();
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al parsear las horas");
            }
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Horario no encontrado");
    }

}
