package com.mrmachine.pizza.persistence.audit;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/*
 * Clase que sirve para retornar la info a los atributos de user
 * en la clase de auditoria llamada AuditabelEntity
 * */
@Service
public class AuditUsername implements AuditorAware<String> {

	@Override
	public Optional<String> getCurrentAuditor() {
		
		Authentication auth = SecurityContextHolder
				.getContext()
				.getAuthentication();
		
		if (auth == null || !auth.isAuthenticated()) return null;
		
		String username = auth.getPrincipal().toString();
		
		return Optional.of(username);
	}
	
}
