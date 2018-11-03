package com.core.app.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class SwaggerConfiguration {

	@Bean
	public Docket  productApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.any())
				.build();
	}

	private ApiInfo metaInfo() {
		return new ApiInfo(
				"Pet Care",
				"API Documentation Pet Care",
				"1.0.0",
				"Pet Care Application",
				new Contact("PetCare", "", ""),
				"",
				""
		);
	}
}
