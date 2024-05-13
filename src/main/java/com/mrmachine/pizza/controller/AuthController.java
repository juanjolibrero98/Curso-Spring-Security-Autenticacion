package com.mrmachine.pizza.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mrmachine.pizza.config.JwtUtil;
import com.mrmachine.pizza.service.dto.LoginDTO;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	private final AuthenticationManager authenticationManager;
	private final JwtUtil jwtUtil;

    AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
		this.jwtUtil = jwtUtil;
    }

	@PostMapping("/login")
	public ResponseEntity<Void> login(@RequestBody LoginDTO user) {
		UsernamePasswordAuthenticationToken login = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
		/*
		 * Aqui autenticamos el usuario verifica si es el mismo usario que se encuentra en bd
		 * y el proceso de autenticacion
		 * */
		Authentication authentication = this.authenticationManager.authenticate(login);
		System.out.println("Verificacion de la autenticacion");
		System.out.println("   Esta autenticado: " + authentication.isAuthenticated());
		System.out.println("   usr logeado: " + authentication.getPrincipal());
		
		/*
		 * Crear el JWT asignarselo y enviarselo en su peticion al usario en cuestion 
		 * */
		String jwt = this.jwtUtil.create(user.getUsername());
		
		return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, jwt).build();
	}
	
}
