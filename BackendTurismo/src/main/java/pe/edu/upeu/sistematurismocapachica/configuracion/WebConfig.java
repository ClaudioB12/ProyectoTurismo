package pe.edu.upeu.sistematurismocapachica.configuracion;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Exponer archivos desde uploads/fotos
        registry.addResourceHandler("/uploads/fotos/**")
                .addResourceLocations("file:uploads/fotos/");
    }
}
