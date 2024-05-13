package com.mrmachine.pizza.persistence.audit;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;



/*
 * Clase para auditoria que sirve para que las entitys
 * tengan estos 2 atributos osea los herenden para que en su tabla referente
 * se pueda saber cuando fueron modificadas o creadas
 * por eso es importante marcarlas con @MappedSuperclass, y @CreatedDate
 * @LastModifiedDate
 * */
@MappedSuperclass
public class AuditabelEntity {
	@Column(name = "created_date")
	@CreatedDate
	private LocalDateTime createdDate;
	
	@Column(name = "modifed_date")
	@LastModifiedDate
	private LocalDateTime modifedDate;
	
	/*
	 * Estos atributos son para
	 * especificar quien modifico las pizzas
	 * esto se hace gracias a la conexion de Spring JPA
	 * y Spring Security
	 * */
	@CreatedBy
	@Column(name = "created_by")
	private String createdBy;
	
	@LastModifiedBy
	@Column(name = "modifed_by")
	private String modifedBy;
	
}
