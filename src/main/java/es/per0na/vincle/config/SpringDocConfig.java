package es.per0na.vincle.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class SpringDocConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Vincle")
                        .version("1.0")
                        .description("Documentation for the Vincle API"));
    }

    @Bean
    public GroupedOpenApi itemsOpenApi() {
        return GroupedOpenApi.builder()
                .group("items")
                .pathsToMatch("/api/items/**")
                .build();
    }

    @Bean
    public GroupedOpenApi eventsOpenApi() {
        return GroupedOpenApi.builder()
                .group("events")
                .pathsToMatch("/api/events/**")
                .build();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
