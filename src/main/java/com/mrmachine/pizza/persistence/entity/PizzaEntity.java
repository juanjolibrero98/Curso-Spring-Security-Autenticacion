package com.mrmachine.pizza.persistence.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mrmachine.pizza.persistence.audit.AuditPizzaListener;
import com.mrmachine.pizza.persistence.audit.AuditabelEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
 * Este @ lo que activa es que esta clase va ser auditada.
 * 
 * por lo que se crea una super clase que lo que hace es tener 2 atributos:
 * 
 * @Column(name = "created_date")
	@CreatedDate
	private LocalDateTime createdDate;
	
	@Column(name = "modifed_date")
	@LastModifiedDate
	private LocalDateTime modifedDate;
 * 
 * que se mapean junto con la entity y la tabla para saber cuando se creo
 * y actualizo la entidad y mostrarla en su tabla referente, la entity hereda
 * estos atributos.
 * 
 * la clase AuditPizzaListener.class sirve para crear un auditoria
 * personalizada cuando la entidad sea cargada, actualizada, guardada o eliminada. 
 * 
 * OJO LA AUDITORIA SOLO SIRVE SI ESTA DENTRO DEL CICLO DE VIDA DE JPA, 
 * POR EOS LOS QUERYS NATIVOS NO DETECTAN ESTAS AUDITORIAS.
 * */
@EntityListeners({AuditingEntityListener.class, AuditPizzaListener.class})
@Entity
@Table(name = "pizza")
@NoArgsConstructor
@Getter
@Setter
public class PizzaEntity extends AuditabelEntity implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_pizza", nullable = false)
	private Integer idPizza;
	
	@Column(nullable = false, length = 30, unique = true)
	private String name;
	
	@Column(nullable = false, length = 150)
	private String description;
	
	@Column(nullable = false, columnDefinition = "Decimal(5,2)")
	private Double price;
	
	@Column(columnDefinition = "BOOLEAN")
	private Boolean vegetarian;
	
	@Column(columnDefinition = "BOOLEAN")
	private Boolean vegan;
	
	@Column(columnDefinition = "BOOLEAN", nullable = false)
	private Boolean available;
	
	@JsonIgnore
	@OneToOne(mappedBy = "pizza", cascade = CascadeType.ALL, orphanRemoval = true , fetch = FetchType.LAZY)
	private OrderItemEntity orderItem;

	@Override
	public String toString() {
		return "PizzaEntity [idPizza=" + idPizza + ", name=" + name + ", description=" + description + ", price="
				+ price + ", vegetarian=" + vegetarian + ", vegan=" + vegan + ", available=" + available
				+ ", orderItem=" + orderItem + "]";
	}
}
