package com.mrmachine.pizza.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mrmachine.pizza.persistence.entity.CustomerEntity;
import com.mrmachine.pizza.persistence.entity.OrderEntity;
import com.mrmachine.pizza.service.CustomerService;
import com.mrmachine.pizza.service.OrderService;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
	
	private final CustomerService customerService;
	private final OrderService orderService;

    CustomerController(CustomerService customerService, OrderService orderService) {
        this.customerService = customerService;
        this.orderService = orderService;
    }
	
	@GetMapping("/phone/{phone}")
	public ResponseEntity<CustomerEntity> getByPhone(@PathVariable(name = "phone") String phoneNumber){
		return ResponseEntity.ok(this.customerService.findByPhone(phoneNumber));
	}

	@GetMapping("/customer/{id}")
	public ResponseEntity<List<OrderEntity>> getCustomerOrders( @PathVariable("id") String idCustomer) {
		return ResponseEntity.ok(this.orderService.getCustomerOrders(idCustomer));
	}
}
