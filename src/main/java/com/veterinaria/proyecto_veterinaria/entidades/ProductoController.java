package com.veterinaria.proyecto_veterinaria.entidades;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.veterinaria.proyecto_veterinaria.entidadUsuario.EmpleadoService;
import com.veterinaria.proyecto_veterinaria.entidadUsuario.empleadoLogin;
import com.veterinaria.proyecto_veterinaria.paginacion.PageRender;

@Controller
public class ProductoController {

    ArrayList<Carrito> listaCarrito = new ArrayList();

    @Autowired
    private PropietarioService propietarioService;

    @Autowired
    private VentasService ventasService;

    @Autowired
    private Detalle_ventasService detalle_ventasService;
    
    @Autowired
    private ProductoService productoService;

    @Autowired
    private CategoriaService categoriaService;
    
    @Autowired
    private EmpleadoService empleadoService;

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
        int TotalCantidad = 0;
        for (int i = 0; i < listaCarrito.size(); i++) {
            TotalCantidad += listaCarrito.get(i).getCantidad();
        }
        model.addAttribute("TotalCantidad",TotalCantidad);
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

    @PostMapping("/agregarCarrito")
    public String AgregarCarrito(@RequestParam("id") int id_producto,
            @RequestParam("nombre") String nombre,
            @RequestParam("precio") float precio,
            @RequestParam("cantidad") int cantidad,
            @RequestParam("dscto") float dscto,
            @RequestParam(name = "page",defaultValue = "0")int page, Model model) 
    {
        Pageable pageRequest = PageRequest.of(page,10);
        Page<Producto> producto = productoService.findAll(pageRequest);
        PageRender<Producto> pageRender = new PageRender<>("/catalogoProducto",producto);
        model.addAttribute("producto",producto);
        model.addAttribute("page",pageRender);
        Carrito c = new Carrito();
        float Total = (precio - dscto) * cantidad;
        
        c.setIdProducto(id_producto);
        c.setNombre(nombre);
        c.setPrecio(precio);
        c.setDscto(dscto);
        c.setCantidad(cantidad);
        c.setTotal(Total);

        listaCarrito.add(c); 

        return "redirect:/catalogoProducto";
    }

    @GetMapping("/obtenerDNI")
    @ResponseBody
    public List<Map<String, String>> obtenerPropietariosPorDni(@RequestParam(value = "dni", required = false, defaultValue = "") String dni) {
        List<Propietario> propietarios = propietarioService.findByDniLike("%" + dni + "%");
        List<Map<String, String>> propietariosJson = new ArrayList<>();

        for (Propietario propietario : propietarios) {
            Map<String, String> propietarioJson = new HashMap<>();
            propietarioJson.put("id", String.valueOf(propietario.getId()));
            propietarioJson.put("dni", String.valueOf(propietario.getDni()));
            propietarioJson.put("nombre", propietario.getNombre());
            propietariosJson.add(propietarioJson);
        }

        return propietariosJson;
    }

    @GetMapping("/registrarCliente")
    @ResponseBody
    public Map<String, String> registrarPropietario(@RequestParam(value = "tipoDocumento") String tipoDocumento,
                                    @RequestParam(value = "numDocR") String numDocR,
                                    @RequestParam(value = "nomCompR") String nomCompR,
                                    @RequestParam(value = "emailR") String emailR,
                                    @RequestParam(value = "direcR", required = false, defaultValue = "") String direcR,
                                    @RequestParam(value = "celR", required = false, defaultValue = "0") String celR) {
        
        Map<String, String> clienteJson = new HashMap<>();
        boolean existeCliente = propietarioService.buscarCliente(numDocR);
        if (existeCliente !=false) {
            clienteJson.put("respuesta","error");
        } else {
            Propietario propietario = new Propietario();
            propietario.setTipoDocumento(tipoDocumento);
            propietario.setDni(Long.parseLong(numDocR));
            propietario.setNombre(nomCompR);
            propietario.setCorreo(emailR);
            propietario.setDireccion(direcR);
            propietario.setCelular(Long.parseLong(celR));
            propietarioService.save(propietario);
            clienteJson.put("dni", String.valueOf(propietario.getDni()));
            clienteJson.put("nombre", propietario.getNombre());
            clienteJson.put("respuesta", "success");
        } 
        
        return clienteJson;
    }

    @GetMapping("/ListarCarrito")
    public String ListarCarrito(Model model) {
        model.addAttribute("detalles", listaCarrito);
        float subTotal = (float) 0.0;
        float igv = (float) 0.0;
        float Total = (float) 0.0;
        for (int i = 0; i < listaCarrito.size(); i++) {
            subTotal += listaCarrito.get(i).getTotal();
        }
        igv = subTotal*(float) 0.18;
        Total = subTotal + igv;
        model.addAttribute("subtotal",subTotal);
        model.addAttribute("igv",igv);
        model.addAttribute("sumar",Total);

        int TotalCantidad = 0;
        for (int i = 0; i < listaCarrito.size(); i++) {
            TotalCantidad += listaCarrito.get(i).getCantidad();
        }
        model.addAttribute("TotalCantidad",TotalCantidad);
        return "carrito";
    }

    @GetMapping("/EliminarCompra")
    public String EliminarCompra(@RequestParam("id") int id, Model model) {
        for (int x = 0; x < listaCarrito.size(); x++) {
            if (listaCarrito.get(x).getIdProducto() == id) {
                listaCarrito.remove(x);
            }
        }
        return "redirect:/ListarCarrito";
    }

    @PostMapping("/ContinuarCompra")
    public String continuarCompra(@RequestParam("dni") String dniCliente,@RequestParam("total") float total,@RequestParam("igv") float igv,@RequestParam("subtotal") float subtotal) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        empleadoLogin empleado = empleadoService.findByEmail(email);
        Ventas venta = new Ventas();
        venta.setDni_cliente(dniCliente);
        venta.setEmpleado(empleado);
        venta.setTipoComprobante("Boleta");
        String tipo_comprobante = venta.getTipoComprobante();
        String numeroSerie = ventasService.generarNumeroSerie(tipo_comprobante);
        venta.setNumeroSerie(numeroSerie);
        String numeroCorrelativo = ventasService.generarNumeroCorrelativo();
        venta.setNumeroCorrelativo(numeroCorrelativo);
        venta.setSubmonto(subtotal);
        venta.setIgv(igv);
        venta.setMonto(total);
        venta.setEstado(1);
        ventasService.save(venta);

        for (Carrito item : listaCarrito) {
            Detalle_ventas detalle = new Detalle_ventas();
            detalle.setVentas(venta);
            detalle.setCantidad(item.getCantidad());
            detalle.setPrecioVenta(item.getTotal());
            detalle.setSubPrecioVenta(item.getPrecio());
            detalle.setDscto(item.getDscto());
            Producto producto = productoService.findOne(item.getIdProducto());
            if (producto != null) {
                detalle.setProducto(producto);
            } else {
            }
            
            detalle_ventasService.save(detalle);
        }
        listaCarrito.clear();
        return "detalleCompra";
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
