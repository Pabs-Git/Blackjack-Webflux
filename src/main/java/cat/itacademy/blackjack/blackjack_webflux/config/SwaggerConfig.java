package cat.itacademy.blackjack.blackjack_webflux.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

    // NEW: Bean definition for OpenAPI metadata
    @Bean
    public OpenAPI blackjackOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Blackjack WebFlux API")
                        .version("1.0.0")
                        .description("Reactive Blackjack API with Spring WebFlux, R2DBC, MongoDB"));
    }
}
