 package com.mrmachine.pizza.persistence.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "order_item")
public class OrderItemEntity implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_item")
	private Integer idItem;
	
	@JsonIgnore
	@JoinColumn(name = "id_pizza", insertable = false, updatable = false)
	@OneToOne(fetch = FetchType.LAZY)
	private PizzaEntity pizza;
	
	@Column(nullable = false, columnDefinition = "Decimal(2,1)")
	private Double quantity;
	
	@Column(nullable = false, columnDefinition = "Decimal(5,2)")
	private Double price;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "id_order", referencedColumnName = "id_order", insertable = false, updatable = false)
	private OrderEntity orderEntity;
}
