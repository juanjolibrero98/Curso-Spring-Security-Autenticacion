package com.mrmachine.pizza.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.mrmachine.pizza.persistence.entity.UserEntity;
import com.mrmachine.pizza.persistence.repository.UserRepository;

@Service
public class UserSecurityService implements UserDetailsService{

	private final UserRepository userRepo;

    UserSecurityService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }
	
    /*
     * Este metodo spring lo lee automaticamente
     * 
     * retorna el usario al filtro de seguiridad para ver si tiene permisos 
     * verifica su autenticidad
     * */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		UserEntity userEntity = this.userRepo.findById(username)
				.orElseThrow( () -> new UsernameNotFoundException("User -> " + username + " not found.") );
		
		String[] roles = userEntity.getRoles().stream().map(r -> r.getRole()).toArray(String[]::new);
		
		
		return User.builder()
				.username(userEntity.getUsername())
				.password(userEntity.getPassword())
				//.roles(roles) // roles es un grupo de permisos te tipo ROLE_
				.authorities(this.grantedAuthorities(roles)) // Un autoriti es un permiso puntual en donde se especifica aun mas el uso de los endpoint ejemplo, tengo acceso a un modulo retringido pero solo puedo leer y no modificar
				.accountLocked(userEntity.getLocked())
				.disabled(userEntity.getDisabled())
				.build();
	}
	
	private String[] getAuthorities(String role) {
		if ( "ADMIN".equals(role) || "CUSTOMER".equals(role) ) {
			return new String[] {"random_order"};
		}
		return new String[] {};
	}
	
	public List<GrantedAuthority> grantedAuthorities(String [] roles) {
		
		List<GrantedAuthority> authorities = new ArrayList<>();
		
		for (String role : roles) {
			
			authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
			
			for (String authority : this.getAuthorities(role)) {
				authorities.add(new SimpleGrantedAuthority(authority));
			}
		}
		
		return authorities;
	}
	
	
}
