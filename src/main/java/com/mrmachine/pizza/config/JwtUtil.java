package com.mrmachine.pizza.config;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;

@Component
public class JwtUtil {
	
	private static String SECRET_KEY = "5b5b5pvB-";
	private static Algorithm ALGORITHM = Algorithm.HMAC256(SECRET_KEY);
	
	/*
	 * Crea el JWT configurando
	 * el algoritmo la fecha de expiracion
	 * las palabras claves para un usuario puntual
	 * */
	public String create(String username) {
		return JWT
				.create()
				.withSubject(username)
				.withIssuer("mr-machine")
				.withIssuedAt(new Date())
				.withExpiresAt( new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(10) ))
				.sign(ALGORITHM);
	}
	
	
	/*
	 * validar JWT
	 * */
	public boolean isvalid(String jwt) {
		try {
			JWT
				.require(ALGORITHM)
				.build()
				.verify(jwt);
			return true;
		} catch (JWTVerificationException e) {
			e.printStackTrace(System.err);
			return false;
		}
	}
	
	/*
	 * Obtener del jwt el usrname
	 * */
	public String getUsername(String jwt) {
		return JWT
				.require(ALGORITHM)
				.build()
				.verify(jwt)
				.getSubject();
	}
	
	
}