package com.starlingdiaz.CatalogSecureCapital;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@OpenAPIDefinition(info = @io.swagger.v3.oas.annotations.info.Info(title = "Catalog Secure Capital", version = "1.0", description = "Documentation Catalog Secure Capital API v1.0"))
public class CatalogSecureCapitalApplication {

	private static final int STRENGHT = 14;

	public static void main(String[] args) {
		SpringApplication.run(CatalogSecureCapitalApplication.class, args);
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(STRENGHT);
	}
}
