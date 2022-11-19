package com.veterinaria.proyecto_veterinaria.Entidad_usuario;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class passwordGenerator {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = "123";
        String encodedPasword = encoder.encode(rawPassword);

        System.out.println(encodedPasword);
    }
}
