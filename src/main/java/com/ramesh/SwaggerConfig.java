package com.ramesh;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket apiV2(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .paths(PathSelectors.regex("/v2/.*"))
                .build()
                .apiInfo(apiInfo("2.0")).groupName("v2");
    }

    @Bean
    public Docket apiV1(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .paths(PathSelectors.regex("/v1/.*"))
                .build()
                .apiInfo(apiInfo("1.0")).groupName("v1");
    }

    private ApiInfo apiInfo(String version) {
        return new ApiInfoBuilder()
                .title("QuickPoll Rest API")
                .description("QuickPoll API for creating and managing polls")
                .version(version)
                .contact(new Contact("Ramesh K P", "http://kpramesh2212@gmail.com",
                        "kpramesh2212@gmail.com"))
                .license("MIT License")
                .licenseUrl("http://opensource.org/licenses/MIT")
                .build();
    }

}
