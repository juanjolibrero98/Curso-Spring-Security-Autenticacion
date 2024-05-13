package com.mrmachine.pizza.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import com.mrmachine.pizza.persistence.entity.PizzaEntity;
import com.mrmachine.pizza.service.dto.UpdatePizzaPriceDTO;


public interface PizzaRepository extends ListCrudRepository<PizzaEntity, Integer>{	

	//Query Method
	List<PizzaEntity> findAllByAvailableTrueOrderByPrice();
	
	//Query Method
	List<PizzaEntity> findByAvailableIsTrueAndNameIgnoreCaseContaining(String name);
	
	//Query Method
	List<PizzaEntity> getByAvailableIsTrueAndDescriptionIgnoringCaseContaining(String receta);
	
	//QueryMethod
	List<PizzaEntity> getByAvailableIsTrueAndDescriptionIgnoringCaseNotContaining(String receta);
	
	List<PizzaEntity> findTop3ByAvailableTrueAndPriceLessThanEqualOrderByPriceAsc(Double price);
	
	
	//QueryMethod
	Integer countByVeganTrue();
	
	//Formas de hacer el UPDATE
	/*
	@Query(value = "UPDATE pizza"
			+ "SET price = :newPrice"
			+ "WHERE id_pizza = :idPizza", nativeQuery = true)
	void updatePrice( @Param("idPizza") int idPizza, @Param("newPrice") double newPrice);
	*/
	@Query(value = "UPDATE pizza "
			+ "SET price = :#{#newPizzaPrice.newPrice} "
			+ "WHERE id_pizza = :#{#newPizzaPrice.pizzaId}", nativeQuery = true)
	@Modifying
	void updatePrice( @Param("newPizzaPrice") UpdatePizzaPriceDTO newPizzaPrice);
	/*
	 * Este metodo lo que hace es actualizar una pizza, utilizando UN DTO de pizza
	 * con un query NATIVO, lo que actualiza es su precio solamente, para ello debe utiliza 
	 * su ID, tambien se muestra la forma de como referenciar los atributos del DTO
	 * con la consulta en su sintaxis ' :#{} '
	 * * */
	
	
	
	
}
