package com.metinkuzey.mcp.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("MCP Service Control API")
                .version("1.0.0")
                .description("API documentation for the MCP Service Control project")
                .contact(new Contact()
                    .name("Metin Kuzey")
                    .email("metinkuzey@example.com")
                )
            );
    }
}
