package com.mrmachine.pizza.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mrmachine.pizza.persistence.entity.OrderEntity;
import com.mrmachine.pizza.projection.OrderSummary;
import com.mrmachine.pizza.service.OrderService;
import com.mrmachine.pizza.service.dto.RandomPizzaDTO;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
	
	public final OrderService orderService;

    OrderController(OrderService orderService) {
        this.orderService = orderService;
    }
    
    @GetMapping("/all")
    public ResponseEntity<List<OrderEntity>> getAll() {
    	return ResponseEntity.ok(this.orderService.getAll());
    }
    
    @GetMapping("/today")
    public ResponseEntity<List<OrderEntity>> getTodayOrders() {
    	return ResponseEntity.ok(this.orderService.getTodayOrders());
    }
    
    @GetMapping("/outside")// Metodo para probar la autorities
    public ResponseEntity<List<OrderEntity>> getOutSideOrders() {
    	return ResponseEntity.ok(this.orderService.getOutSideOrders());
    }
    
    @GetMapping("/customer/{id}")
    public ResponseEntity<List<OrderEntity>> getCustomerOrders( @PathVariable("id") String idCustomer) {
    	return ResponseEntity.ok(this.orderService.getCustomerOrders(idCustomer));
    }
    
    @GetMapping("/summary/{id}")
    public ResponseEntity<OrderSummary> getSummary( @PathVariable("id") Integer orderId) {
    	return ResponseEntity.ok(this.orderService.getSummary(orderId));
    }
    
    @PostMapping("/test/procedure")
    public ResponseEntity<Boolean> ramdonOrder( @RequestBody RandomPizzaDTO dto ) {
    	return ResponseEntity.ok(this.orderService.saveRandomOrder(dto));
    }
}
