package com.mrmachine.pizza;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/*
 * Habilita la auditoria para llevar la trazabilidad de los registros
 * en movimiento del sistema de informacion.
 * 
 * Se habilita tambien la persistencia de datos con JPA
 * 
 * -----------------------------------------------------------------------------------
 * 
 * Esta es la continuacion del curso de spring 
 * 
 * Le vamos a agregar la SPRING SECURITY.
 * 
 * 
 * Cuando activas por defecto la seguridad de Spring
 * se utiliza el basic auth que es basicamente el logeo de usr
 * y contrasena en encabezado de la peticion
 * 
 * igualmente si se observa como un documento html esto creara
 * un login por defecto para que se realice esta misma auth basic
 *
 * */
@EnableJpaAuditing
@SpringBootApplication
@EnableJpaRepositories
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
