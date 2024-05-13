package com.mrmachine.pizza.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mrmachine.pizza.persistence.entity.PizzaEntity;
import com.mrmachine.pizza.service.PizzaService;
import com.mrmachine.pizza.service.dto.UpdatePizzaPriceDTO;

@RestController
@RequestMapping("/api/pizzas")
public class PizzaController {

	private final PizzaService pizzaService;

	PizzaController(PizzaService pizzaService) {
		this.pizzaService = pizzaService;
	}

	@GetMapping("/all")
	public ResponseEntity<List<PizzaEntity>> getAll() {
		return ResponseEntity.ok(this.pizzaService.getAll());
	}

	@GetMapping("/allPage")
	public ResponseEntity<Page<PizzaEntity>> getAll(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "12") int elements) {
		return ResponseEntity.ok(this.pizzaService.getAll(page, elements));
	}

	@GetMapping("/pizza/{idPizza}")
	public ResponseEntity<PizzaEntity> get(@PathVariable Integer idPizza) {
		return ResponseEntity.ok(this.pizzaService.get(idPizza));
	}

	@GetMapping("/pizza/search/{namePizza}")
	public ResponseEntity<List<PizzaEntity>> getAvailableTrueAndGetByName(@PathVariable String namePizza) {
		return ResponseEntity.ok(this.pizzaService.findByAvailableIsTrueAndNameIgnoreCaseContaining(namePizza));
	}

	@GetMapping("/pizza/with/{receta}")
	public ResponseEntity<List<PizzaEntity>> getByPizzaWith(@PathVariable String receta) {
		return ResponseEntity.ok(this.pizzaService.pizzaWith(receta));
	}

	@GetMapping("/pizza/without/{receta}")
	public ResponseEntity<List<PizzaEntity>> getByPizzaWithOut(@PathVariable String receta) {
		return ResponseEntity.ok(this.pizzaService.pizzaWithOut(receta));
	}

	//@CrossOrigin anotacion que permite que se pueda consultar este endpoint desde diferentes frontends
	@GetMapping("/pizza/available")
	public ResponseEntity<List<PizzaEntity>> getAvailable() {
		return ResponseEntity.ok(this.pizzaService.getAvailable());
	}
	
	@GetMapping("/pizza/availableSort")
	public ResponseEntity<Page<PizzaEntity>> getAvailableSort(
				@RequestParam(defaultValue = "0") int page,
				@RequestParam(defaultValue = "8") int elements,
				@RequestParam(defaultValue = "price") String sortBy,
				@RequestParam(defaultValue = "ASC") String sortDirection
			) {
		return ResponseEntity.ok(this.pizzaService.getAvailableSort( page, elements, sortBy, sortDirection));
	}

	@GetMapping("/pizza/cheapest/{price}")
	public ResponseEntity<List<PizzaEntity>> getCheapest(@PathVariable Double price) {
		return ResponseEntity.ok(this.pizzaService.getCheapest(price));
	}

	@PostMapping
	public ResponseEntity<PizzaEntity> add(@RequestBody PizzaEntity pizza) {
		if (pizza.getIdPizza() == null || !this.pizzaService.exists(pizza.getIdPizza()))
			return ResponseEntity.ok(this.pizzaService.save(pizza));
		PizzaEntity response = new PizzaEntity();
		response.setDescription(
				"El objeto ya se encuentra en la Base de Datos, intenta actualizarlo con el method PUT.");
		return ResponseEntity.badRequest().body(response);
	}

	@PutMapping
	public ResponseEntity<PizzaEntity> update(@RequestBody PizzaEntity pizza) {
		if (pizza.getIdPizza() != null && this.pizzaService.exists(pizza.getIdPizza()))
			return ResponseEntity.ok(this.pizzaService.save(pizza));
		PizzaEntity response = new PizzaEntity();
		response.setDescription(
				"Ups... verifica si colocaste el id, de lo contrario, el objeto no se encuentra en la Base de Datos, intenta agregarlo con el method POST.");
		return ResponseEntity.badRequest().body(response);
	}
	
	@PutMapping("/price")
	public ResponseEntity<Void> updatePrice(@RequestBody UpdatePizzaPriceDTO dto) {
		if ( this.pizzaService.exists(dto.getPizzaId()) ) {
			this.pizzaService.updatePrice(dto);
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.badRequest().build();
	}

	@DeleteMapping("/pizza/{idPizza}")
	public ResponseEntity<Void> delete(@PathVariable Integer idPizza) {
		if (Boolean.TRUE.equals(this.pizzaService.exists(idPizza))) {
			this.pizzaService.delete(idPizza);
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.badRequest().build();
	}
}
