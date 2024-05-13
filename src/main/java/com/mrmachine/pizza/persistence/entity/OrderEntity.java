package com.mrmachine.pizza.persistence.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "pizza_order")
@Getter
@Setter
@NoArgsConstructor
public class OrderEntity  implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_order", nullable = false)
	private Integer idOrder;
	
	@OneToOne
	@JoinColumn(name = "id_customer", nullable = false, referencedColumnName = "id_customer", insertable = false, updatable = false )
	private CustomerEntity customer;
	
	@Column(nullable = false, columnDefinition = "TIMESTAMP")
	private LocalDateTime date;
	
	@Column(nullable = false, columnDefinition = "Decimal(6,2)")
	private Double total;
	
	@Column(nullable = false, columnDefinition = "CHAR(1)")
	private String method;
	
	@Column(name="additional_notes", length = 200)
	private String additionalNotes;
	
	@OneToMany(mappedBy = "orderEntity", fetch = FetchType.EAGER)
	@OrderBy("price ASC") // ANOTACION SIRVE PARA ORDENAR EN EL ATRIBUTO PRICE DE ORDERITEM MANERA ASC
	private List<OrderItemEntity> orderItems;
}
