package com.example.LoyaltyPoints.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI loyaltyApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Loyalty Points API")
                        .description("Event Sourcing based Loyalty Points Management System")
                        .version("1.0"));
    }
}
