package olx_resale_app.olx_resale_app.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList("Bearer Authentication"))
                .components(new Components()
                        .addSecuritySchemes("Bearer Authentication", apiKeyScheme())
                        .addParameters("X-Role-Guest", new Parameter()
                                .in("header")
                                .name("X-Role-Guest")
                                .required(true)
                                .description("Guest role header")
                                .schema(new StringSchema())))
                .info(new Info()
                        .title("OLX Resale App API")
                        .description("API documentation for the OLX Resale App")
                        .version("1.0"));
    }

    private SecurityScheme apiKeyScheme() {
        return new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .bearerFormat("JWT")
                .scheme("bearer");
    }

}
