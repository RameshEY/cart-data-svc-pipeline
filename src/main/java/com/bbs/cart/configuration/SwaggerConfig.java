package com.bbs.cart.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebFlux;

@Configuration
@EnableSwagger2WebFlux
public class SwaggerConfig {

    @Value("${swagger.api.title}")
    private String title;

    @Value("${swagger.api.description}")
    private String description;

    @Value("${swagger.api.contact}")
    private String contact;

    @Value("${swagger.api.licenseUrl}")
    private String licenseUrl;

    @Value("${swagger.api.license}")
    private String license;

    @Value("${swagger.api.version}")
    private String version;

    @Value("${swagger.api.email}")
    private String email;

    @Value("${swagger.api.url}")
    private String url;

    @Value("${swagger.api.name}")
    private String name;

    @Value("${swagger.api.basePackage}")
    private String basePackage;

    @Bean
    public Docket api() {

        return new Docket(DocumentationType.SWAGGER_2).select()
                .apis(RequestHandlerSelectors
                        .basePackage(basePackage))
                .paths(PathSelectors.any())
                .build().apiInfo(apiEndPointsInfo());
    }

    private ApiInfo apiEndPointsInfo() {

        return new ApiInfoBuilder().title(title)
                .description(description)
                .contact(new Contact(name, url, email))
                .license(license)
                .licenseUrl(licenseUrl)
                .version(version)
                .build();
    }

}