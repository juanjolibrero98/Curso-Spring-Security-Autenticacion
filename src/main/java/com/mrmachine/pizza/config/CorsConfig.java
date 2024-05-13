package com.mrmachine.pizza.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class CorsConfig {

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration corsConfi = new CorsConfiguration();
		
		// Origines permitidos
		// Permite desde que origen pueden consumir nuestra api
		corsConfi.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
		// Que metodos tendra permitidos
		corsConfi.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE"));
		// que lleguen todos los headers
		corsConfi.setAllowedHeaders(Arrays.asList("*"));
		
		// Todos los controladores se les aplicara el Cors con la configuracion de corsConfi
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", corsConfi);
		
		return source;
	}
}
