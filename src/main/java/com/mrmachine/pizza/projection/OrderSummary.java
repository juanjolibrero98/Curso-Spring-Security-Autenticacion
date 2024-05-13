package com.mrmachine.pizza.projection;

import java.time.LocalDateTime;

/*
 * Esto es una simulacion de un DTO,
 * esta interfaz permite almacenar la informacion de un 
 * query Personalizado y alojarla en los metodos de abajo,
 * respetando la escritura de sus nombres en el select EJ:
 * + "		po.id_order AS idOrder,  "
   + "		cu.name AS customerName,  "
   + "		po.date AS orderDate,  "
   + "		po.total AS orderTotal,  "
   + "		STRING_AGG(pi.name, ',') AS pizzaNames   "
   
 * Despues de los 'AS' es la similitud de los nombres de los metodos
 * se debe colocar siempre la letra en minuscula para que lea correctamente
 * que hace referencia a los metodos de abajo
 * */
public interface OrderSummary {
	
	Integer getIdOrder();
	
	String getCustomerName();
	
	LocalDateTime getOrderDate();
	
	Double getOrderTotal();
	
	String getPizzaNames();
	
}
