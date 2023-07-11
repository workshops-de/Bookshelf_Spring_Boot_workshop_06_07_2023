package de.workshops.bookshelf;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {
    @Value("${application.version:x.x.x}")
    private String appVersion;

    @Bean
    public OpenAPI api(BookshelfProperties bookshelfProperties) {
        return new OpenAPI()
                .info(
                        new Info()
                                .title(bookshelfProperties.getName())
                                .version(appVersion)
                                .license(new License()
                                        .name(bookshelfProperties.getLicense().getName())
                                        .url(bookshelfProperties.getLicense().getUrl().toString())
                                )
                );
    }
}
