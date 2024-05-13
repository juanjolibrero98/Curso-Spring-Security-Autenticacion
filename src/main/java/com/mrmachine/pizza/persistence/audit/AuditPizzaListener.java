package com.mrmachine.pizza.persistence.audit;

import org.springframework.util.SerializationUtils;

import com.mrmachine.pizza.persistence.entity.PizzaEntity;

import jakarta.persistence.PostLoad;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostUpdate;
import jakarta.persistence.PreRemove;


/*
 * Esta clase se utiliza para crear un Listener Personalizado para
 * escuchar los cambios realizados en una entidad ej PIZZA
 * 
 * */
public class AuditPizzaListener {
	
	private PizzaEntity currentValue;
	
	@PostLoad
	public void postLoad(PizzaEntity pizza) {
		System.out.println("POST LOAD");
		this.currentValue = SerializationUtils.clone(pizza);
	}
	
	
	@PostPersist
	@PostUpdate
	public void onPostPersist(PizzaEntity pizza) {
		System.out.println("POST PERSIST OR UPDATE");		
		System.out.println("OLD VALUE: " + this.currentValue.toString());
		System.out.println("NEW VALUE: " + pizza.toString());
	}
	
	
	@PreRemove
	public void onPreDelete(PizzaEntity pizza) {
		System.out.println(pizza.toString());
	}
	
	
}
