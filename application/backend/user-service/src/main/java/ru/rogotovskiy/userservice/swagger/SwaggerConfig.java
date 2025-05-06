package ru.rogotovskiy.userservice.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI api() {
        return new OpenAPI()
                .servers(
                        List.of(
                                new Server().url("http://109.106.139.133:8084")
                        )
                )
                .info(
                        new Info()
                                .title("GigaGuide User API")
                                .version("1.0.0")
                                .contact(
                                        new Contact()
                                                .name("TP 9.2")
                                                .email("https://github.com/LatinMapsLives/GigaGuide")
                                )
                );
    }
}
