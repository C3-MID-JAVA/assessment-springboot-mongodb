package co.com.sofka.cuentabancaria.config.swagger;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi customApi() {
        return GroupedOpenApi.builder()
                .group("default")  // Nombre del grupo
                .pathsToMatch("/**")  // Rutas que serán incluidas en la documentación
                .build();
    }
}
