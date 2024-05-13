package com.mrmachine.pizza.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.mrmachine.pizza.config.JwtFilter;


/** 
 * Clase que tendra toda la configuracion de mi proyecto,
 * por eso se anota con.
 * Esto para que spring detecte este bean para que lo
 * detecte y lo autogestione
 * 
 * */
@Configuration /* No solo se proteje los controlladores si no las reglas de negocio  */
@EnableMethodSecurity(securedEnabled = true) // Esto es la habilitacion para poder utilizar la manera de impedir el acceso a los endpoints de una manera mas elegante, anotandoles en la capa de servicio con @Secured("ROLE_ADMIN") del metodo de servicio a restringir
public class SecurityConfig {
	
	private final JwtFilter jwtFilter;

    SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }
	
	/*
	 * metodo que configura el filtro de seguridad de las peticiones http:
	 * EXPLICACION POR LINEA
	 * */
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		// Se recibe la peticion
		
		
		http //Con el authorizeHttpRequests() recibe un obj que customiza la peticion
			.authorizeHttpRequests( customizeRequest -> {
				/*
				 * Establece que cualquier peticion pueda ser consumida
				 * con customizeRequest .anyRequest().permitAll();
				 * 
				 * Pero le diremos que cualquier peticion debe estar auntenticada y con 
				 * http basic, la autenticacion basica. 
				 * */
				customizeRequest
					.requestMatchers("/api/auth/**").permitAll()
					/*
					 * los requestMatchers sirve p[ara definir reglas
					 * de permitir denegar o permitir el acceso a ciertos
					 * endpoints.
					 * 
					 * .requestMatchers(HttpMethod.GET, "/api/pizzas/**").permitAll()
					 * 	Permite que se consuma metodo get del controlador pizzas
					 * 
					 * o podemos agregar hasAnyRole(...) y agregar los roles para que
					 * ciertos usuarios puedan consumir este controller
					 * 
					 * */
					//.requestMatchers(HttpMethod.GET, "/api/pizzas/**").permitAll()
					.requestMatchers(HttpMethod.GET, "/api/pizzas/**").hasAnyRole("ADMIN","CUSTOMER")
					/*
					 * Denegacion total en los controladores para actualizar
					 * */
					//.requestMatchers(HttpMethod.PUT).denyAll()
					/*
					 * Solo los usuarios que son admins pueden agregar pizzas
					 * */
					.requestMatchers(HttpMethod.POST, "/api/pizzas/**").hasRole("ADMIN")
					/*
					 * El orden de acceso a los endpoints es importante, primero van los mas
					 * especificos y luego los generales
					 * esta politica indica que los usuarios que tengan random_order accedan
					 * a este endpoint especifico, ya que mas abajo se indica que solo los admin 
					 * pueden acceder a los endpoints orders
					 * */
					.requestMatchers("/api/orders/outside").hasAuthority("random_order")
					/*
					 * Solo los admins pueden alterar todo sobre las ordeners
					 * */
					.requestMatchers("/api/orders/**").hasRole("ADMIN")
					/*
					 * 
					 * */
					.requestMatchers("/api/customers/**").hasAnyRole("ADMIN","CUSTOMER")
					.anyRequest()
					.authenticated();
				/**
				 * 
				 * Aplicando BasicAuthenticationFilter :
				 * Basicamente es el administrador que verifica si el usr y pass son correctos
				 * 
				 * httpBasic()
				 * 
				 * -----------------------------------------------
				 * 
				 * csrf()
				 * 
				 * La proteccion csrf es la que evita que en la session
				 * abierta en una app en internet no se pueda replicar
				 * un formulario en difretenes sitios que no sean de la app
				 * 
				 * esto es un peligro porque podemos crear un formulario identico
				 * para hacer maldades y mandar info al backend ya que el servidor
				 * no sabra si la peticion se hizo directamente del sitio web o desde 
				 * un sitio malintencionado
				 * 
				 * ------------------------------------------------------------------
				 *
				 * cors()
				 * 
				 * CORS -> Cross Origin Resource Sharing
				 * 
				 * Esto es una politica que sencillamente bloquea las peticiones
				 * que no sean del origen en donde esta el backend.
				 * 
				 * 
				 * 
				 *  */
			})
				.csrf( AbstractHttpConfigurer::disable)
				.cors(Customizer.withDefaults())
				.sessionManagement( session -> 
					/* Dicinedo que nuestra app no tendra sessiones 
					 * Y la proteccion la tendra jwtFilter
					 * */
					session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				)
				/*
				 * Debemos cambiar nuestro porceso de seguridad
				 * quitando la uatenticacion basica y diciendo
				 * que utilizaremos el nuevo filtro de con JWT TOKEN
				 * -> jwtFilter
				 * */
				//.httpBasic(Customizer.withDefaults())
				.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
				
		
					
		return http.build();
	}
	
	/*
	 * Este metodo permite crear un usuario en memory
	 * */
	//@Bean
	//public UserDetailsService memoryUsers() {
		
		/*
		 * Crear usuario con permisos de ADMIN
		 * */
	//	UserDetails admin = User.builder()
		//		.username("admin")
				/*
				 * Spring Security por seguridad necesita que las pass
				 * esten codificadas asi que si dejamos tal cual el
				 * metodo password("admin") lanzara un error, pidiendo
				 * codificacion, podemos omitir colocando {noop}
				 * 
				 *  podemos utilizar el passwordEncoder() para codificar la pass
				 * */
				//.password("{noop}admin")
				//.password(passwordEncoder().encode("admin"))
				//.roles("ADMIN")
				//.build();
		
		/*
		 * Crear usuario con permisos de customer
		 * */
		//UserDetails customer = User.builder()
				//.username("customer")
			//	.password(passwordEncoder().encode("customer123"))
			//	.roles("CUSTOMER")
				//.build();
		
		//return new InMemoryUserDetailsManager(admin, customer);
	//}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}
	
}
