package com.veterinaria.proyecto_veterinaria.Entidad_usuario;



import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.veterinaria.proyecto_veterinaria.Entidades.Rol;

@Service
public class EmpleadoServiceImpl implements Empleado_Service{

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
   
    @Autowired
    private Empleado_Repositoriy empleado_Repositoriy;

    @Override
    @Transactional(readOnly = true)
    public List<Empleado_Login> findAll() {
        return (List<Empleado_Login>) empleado_Repositoriy.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Empleado_Login> findAll(Pageable pageable) {
        return empleado_Repositoriy.findAll(pageable);
    }

    @Override
    @Transactional
    public void save(Empleado_Login empleado_Login) {
        if(empleado_Login.getId()==null){
            empleado_Login = new Empleado_Login(empleado_Login.getDni(), empleado_Login.getNombre(), empleado_Login.getApellido(), empleado_Login.getFecha_Nacimiento(), empleado_Login.getCelular(), empleado_Login.getEmail(), empleado_Login.getDireccion(), empleado_Login.getUsuario(), passwordEncoder.encode(empleado_Login.getPassword()), empleado_Login.getSexo(), empleado_Login.getTipo_rol());
        }
        if(empleado_Login.getId()!=null){
            empleado_Login.setPassword(passwordEncoder.encode(empleado_Login.getPassword()));
        }
       
        empleado_Repositoriy.save(empleado_Login);
        
    }

    @Override
    @Transactional
    public Empleado_Login findOne(long id) {
        return empleado_Repositoriy.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        empleado_Repositoriy.deleteById(id);
        
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Empleado_Login usuario = empleado_Repositoriy.findByEmail(username);
        if(usuario == null){
            throw new UsernameNotFoundException("Usuario o password inválidos");
        }
        return new User(usuario.getEmail(), usuario.getPassword(), mapearAutoridadesRoles(usuario.getTipo_rol()));
    }

    private Collection<? extends GrantedAuthority> mapearAutoridadesRoles(Collection<Rol> roles){
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getNombre())).collect(Collectors.toList());
    }
    
}
