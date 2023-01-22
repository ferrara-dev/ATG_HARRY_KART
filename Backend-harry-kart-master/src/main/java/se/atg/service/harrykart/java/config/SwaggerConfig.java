package se.atg.service.harrykart.java.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@Configuration
@EnableSwagger2
public class SwaggerConfig {

    ApiInfo DEFAULT_API_INFO=ApiInfo.DEFAULT;
    @Bean
    public Docket api(){

        return new Docket(DocumentationType.SWAGGER_2);
    }
}
