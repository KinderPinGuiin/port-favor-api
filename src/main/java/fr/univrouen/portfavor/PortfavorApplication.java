package fr.univrouen.portfavor;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;

@OpenAPIDefinition
@SpringBootApplication(exclude = { UserDetailsServiceAutoConfiguration.class })
public class PortFavorApplication {

	public static void main(String[] args) {
		SpringApplication.run(PortFavorApplication.class, args);
	}

}
