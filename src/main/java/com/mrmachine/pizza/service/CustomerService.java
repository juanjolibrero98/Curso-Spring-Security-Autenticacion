package com.mrmachine.pizza.service;

import org.springframework.stereotype.Service;

import com.mrmachine.pizza.persistence.entity.CustomerEntity;
import com.mrmachine.pizza.persistence.repository.CustomerRepository;

@Service
public class CustomerService {
	
	private final CustomerRepository customerRepo;

    CustomerService(CustomerRepository customerRepo) {
        this.customerRepo = customerRepo;
    }
    
    public CustomerEntity findByPhone(String phoneNumber) {
    	return this.customerRepo.findByPhone(phoneNumber);
    }
}
