package com.tmrs.poc.app.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenApiConfig {
	
	@Value("${tmrs.openapi.dev-url}")
	private String devUrl;

	@Value("${tmrs.openapi.prod-url}")
	private String prodUrl;

    @Bean
    public OpenAPI usersMicroserviceOpenAPI() {
    	Server devServer = new Server();
        devServer.setUrl(devUrl);
        devServer.setDescription("Development environment");
        
        Server prodServer = new Server();
        prodServer.setUrl(prodUrl);
        prodServer.setDescription("Production environment");
        
        Contact contact = new Contact();
        contact.setEmail("admin@tmrs.com");
        contact.setName("TMRS Admin");
        contact.setUrl("https://www.tmrs.com");
        
        License mitLicense = new License().name("MIT License").url("https://choosealicense.com/licenses/mit/");
        
        Info info = new Info()
        		.title("TMRS NextGen POC")
                .version("1.0")
                .description("Proof of Concept for a spring boot nextgen app.")
                .license(mitLicense)
                .contact(contact);
        
        return new OpenAPI().info(info).servers(List.of(devServer, prodServer));
    }
}