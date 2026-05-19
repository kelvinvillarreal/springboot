package br.com.kelvinvillarreal.mybudget.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * OpenAPI/Swagger configuration for API documentation
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("MyBudget API")
                        .version("1.0.0")
                        .description("API de Planificación Presupuestaria - Arquitectura de Microservicios con Spring Boot")
                        .contact(new Contact()
                                .name("Kelvin Villarreal")
                                .email("kelvin@example.com")
                                .url("https://github.com/kelvinvillarreal"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0.html")));
    }
}
