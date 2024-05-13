package com.mrmachine.pizza.persistence.entity;


import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "customer")
public class CustomerEntity implements Serializable {
	
	@Id
	@Column(name = "id_customer", nullable = false, length = 15, unique = true)
	private String idCustomer;
	
	@Column(nullable = false, length = 60)
	private String name;
	
	@Column(nullable = false, length = 100)
	private String address;
	
	@Column(nullable = false, length = 50, unique = true)
	private String email;
	
	@Column(name = "phone_number", length = 20)
	private String phoneNumber;
	
}
