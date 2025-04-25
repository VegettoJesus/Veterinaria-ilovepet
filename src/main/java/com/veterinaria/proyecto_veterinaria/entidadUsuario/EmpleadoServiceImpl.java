package com.veterinaria.proyecto_veterinaria.entidadUsuario;



import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.veterinaria.proyecto_veterinaria.entidades.Rol;

@Service
public class EmpleadoServiceImpl implements EmpleadoService{

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
   
    @Autowired
    private empleadoRepository empleadoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<empleadoLogin> findAll() {
        return (List<empleadoLogin>) empleadoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<empleadoLogin> findAll(Pageable pageable) {
        return empleadoRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public void save(empleadoLogin empleadoLogin) {
        if(empleadoLogin.getId()==null){
            empleadoLogin = new empleadoLogin(empleadoLogin.getDni(), empleadoLogin.getNombre(), empleadoLogin.getApellido(), empleadoLogin.getFechaNacimiento(), empleadoLogin.getCelular(), empleadoLogin.getEmail(), empleadoLogin.getDireccion(), empleadoLogin.getUsuario(), passwordEncoder.encode(empleadoLogin.getPassword()), empleadoLogin.getSexo(), empleadoLogin.getTipoRol(), empleadoLogin.getImagen());
        }
        if(empleadoLogin.getId()!=null){
            empleadoLogin.setPassword(passwordEncoder.encode(empleadoLogin.getPassword()));
        }
       
        empleadoRepository.save(empleadoLogin);
        
    }

    @Override
    @Transactional
    public empleadoLogin findOne(long id) {
        return empleadoRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        empleadoRepository.deleteById(id);
        
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        empleadoLogin usuario = empleadoRepository.findByEmail(username);
        if(usuario == null){
            throw new UsernameNotFoundException("Usuario o password inválidos");
        }
        return new User(usuario.getEmail(), usuario.getPassword(), mapearAutoridadesRoles(usuario.getTipoRol()));
    }

    private Collection<? extends GrantedAuthority> mapearAutoridadesRoles(Collection<Rol> roles){
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getNombre())).collect(Collectors.toList());
    }

    @Override
    public empleadoLogin findByEmail(String email) {
        return empleadoRepository.findByEmail(email);
    }
    
}
