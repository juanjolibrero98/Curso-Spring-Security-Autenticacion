package com.mrmachine.pizza.persistence.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import com.mrmachine.pizza.persistence.entity.OrderEntity;
import com.mrmachine.pizza.projection.OrderSummary;

public interface OrderRepository extends ListCrudRepository<OrderEntity, Integer>{
	
	//Query Method
	List<OrderEntity> findAllByDateAfter(LocalDateTime date);
	
	//Query Method
	List<OrderEntity> findAllByMethodIn(List<String> methods);
	
	//Native Query
	@Query(value = "SELECT * FROM pizza_order WHERE id_customer = :id", nativeQuery = true)
	List<OrderEntity> findCustomerOrders( @Param("id") String idCustomer);
	
	@Query(value = "SELECT   "
			+ "		po.id_order AS idOrder,  "
			+ "		cu.name AS customerName,  "
			+ "		po.date AS orderDate,  "
			+ "		po.total AS orderTotal,  "
			+ "		STRING_AGG(pi.name, ',') AS pizzaNames   "
			+ "	FROM   "
			+ "		pizza_order po  "
			+ "			JOIN   "
			+ "		customer cu ON po.id_customer = cu.id_customer  "
			+ "			JOIN  "
			+ "		order_item oi ON po.id_order = oi.id_order  "
			+ "			JOIN  "
			+ "		pizza pi ON oi.id_pizza = pi.id_pizza  "
			+ "	WHERE po.id_order = :orderId "
			+ "	GROUP BY po.id_order, cu.name, po.date, po.total", nativeQuery = true)
	OrderSummary findSummary( @Param("orderId") Integer orderId );
	
	
	//Ejecutar un proceso almacenado en la base de datos.
	@Procedure(procedureName = "test_procedure")
	Boolean saveRandomOrder(@Param("nombre") String nombre, @Param("precio") Double precio);
	
}
