package com.mrmachine.pizza.service.dto;

import lombok.Data;

/*
 * Esto es un dto DATA ACCES OBJECT
 * es poder utilizar info de un objeto ya existente para no 
 * exponer el objeto principal
 * */
@Data
public class UpdatePizzaPriceDTO {
	private int pizzaId;
	private double newPrice;
}
