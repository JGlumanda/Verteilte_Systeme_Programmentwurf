package de.jglumanda.verteiltesysteme_programmentwurf.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().info(new Info()
                        .title("Blackboard API")
                        .version("1.0")
                        .description("API for managing blackboards")
                        .contact(new Contact().email("berger.jonas-it21@it.dhbw-ravensburg.de").name("Jonas Berger"))
                        .license(new License().name("MIT License").url("https://opensource.org/licenses/MIT")));
    }
}
