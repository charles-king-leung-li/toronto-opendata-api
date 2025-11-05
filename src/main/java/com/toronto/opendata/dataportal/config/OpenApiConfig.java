package com.toronto.opendata.dataportal.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI torontoOpenDataAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Toronto Open Data API")
                        .description("REST API for accessing Toronto Open Data resources")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("City of Toronto")
                                .url("https://www.toronto.ca/city-government/data-research-maps/open-data/"))
                        .license(new License()
                                .name("Open Government Licence – Toronto")
                                .url("https://open.toronto.ca/open-data-license/")));
    }
}