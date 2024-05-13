package com.mrmachine.pizza.config;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


/*
 * Este nuevo filtro valida las peticiones 
 * que lleguen a nuestra api
 * este filtro podra capturarla y validar la info
 * que este adentro
 * */
@Component
public class JwtFilter extends OncePerRequestFilter{
	
	private final JwtUtil jwtUtil;
	private final UserDetailsService userDetailService;

    JwtFilter(JwtUtil jwtUtil, UserDetailsService userDetailService) {
        this.jwtUtil = jwtUtil;
        this.userDetailService = userDetailService;
    }

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		/*
		 * Para validar un JWT
		 * */
		
		// 1. validar que sea un Header Authorization valido
		String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		// Si no me sirve decimos a la cadena que continue porque le header no se encuentra que contienue su camino
		if (authHeader == null || authHeader.isEmpty() || !authHeader.startsWith("Bearer")) {
			filterChain.doFilter(request, response);
			return;
		}
		
		// 2. Validar que el JWT sea valido
		String jwt = authHeader.split(" ")[1].trim();
		if (!this.jwtUtil.isvalid(jwt)) {
			filterChain.doFilter(request, response);
			return;
		}
		
		// 3. Cargar el usuario del UserDetailsService
		String username = this.jwtUtil.getUsername(jwt);
		User user = (User) this.userDetailService.loadUserByUsername(username);
		
		// 4. Cargar al usuario en el contexto de seguridad.
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
				user.getUsername(),
				user.getPassword(),
				user.getAuthorities()
				);
		authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		
		SecurityContextHolder.getContext().setAuthentication(authenticationToken);
		System.out.println(authenticationToken);
		filterChain.doFilter(request, response);
	}

}
