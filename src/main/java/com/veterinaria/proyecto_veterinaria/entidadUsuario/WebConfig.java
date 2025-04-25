package com.veterinaria.proyecto_veterinaria.entidadUsuario;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/imagenPerfil/**")
                .addResourceLocations("file:src/main/resources/static/imagenPerfil/");
    }
}
